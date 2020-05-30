package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.admin.SearchSearchDTO;
import cc.liqingsong.database.entity.*;
import cc.liqingsong.database.mapper.SearchMapper;
import cc.liqingsong.database.vo.admin.SearchListVO;
import cc.liqingsong.service.admin.ArticleCategoryService;
import cc.liqingsong.service.admin.SearchService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Search 服务实现类
 *
 * @author liqingsong
 */
@Service
public class SearchServiceImpl  extends ServiceImpl<SearchMapper, Search> implements SearchService{

    private ArticleCategoryService articleCategoryService;
    @Autowired
    public void setArticleCategoryService(ArticleCategoryService articleCategoryService) {
        this.articleCategoryService = articleCategoryService;
    }

    /**
     * 排序字段
     */
    private String[] sort = new String[]{"sid","addtime"};
    public String getSort(int i ) {
        int length = sort.length - 1;
        return i > length ? sort[0] : sort[i];
    }

    /**
     * 插入一条记录
     *
     * @param entity 实体类对象
     */
    @Override
    public boolean save(Search entity) {

        if (null == entity) {
            return false;
        }

        Assert.fail(null == entity.getId(), ResultCode.ID_NOT_FOUND);
        Assert.fail(null == entity.getTitle(), ResultCode.TITLE_NOT_EMPTY);

        return super.save(entity);
    }

    /**
     * 根据 ID 修改
     *
     * @param entity 实体类对象
     */
    @Override
    public boolean updateById(Search entity) {

        if (null == entity) {
            return false;
        }

        Assert.fail(null == entity.getId(), ResultCode.ID_NOT_FOUND);

        boolean remove = remove(entity);
        // Assert.fail(!remove, ResultCode.FAIL);


        return this.save(entity);
    }


    @Override
    public boolean remove(Search entity) {
        QueryWrapper<Search> qw = new QueryWrapper<>();
        qw.eq("id",entity.getId());
        qw.eq("type",entity.getType());
        return super.remove(qw);
    }

    @Override
    public IPage<SearchListVO> listVOPage(IPage<Search> page, SearchSearchDTO searchSearchDTO) {
        // 读取文章分页数据
        IPage<Search> list = page(page, searchSearchDTO);

        // 设置返回数据
        Page<SearchListVO> searchListVOPage = new Page<>();
        searchListVOPage.setCurrent(list.getCurrent());
        searchListVOPage.setTotal(list.getTotal());
        searchListVOPage.setPages(list.getPages());
        searchListVOPage.setSize(list.getSize());
        searchListVOPage.setOrders(list.orders());
        searchListVOPage.setOptimizeCountSql(list.optimizeCountSql());
        searchListVOPage.setSearchCount(list.isSearchCount());

        if(list.getRecords().isEmpty()) {
            searchListVOPage.setRecords(new ArrayList<>());
            return searchListVOPage;
        }


        // 取出分类id
        List<Long> cIds = new ArrayList<>();
        for (Search item : list.getRecords() ) {
            if(null != item.getCategoryId() && 0 != item.getCategoryId()) {
                cIds.add(item.getCategoryId());
            }
        }

        // 设置分类
        Map<Long, Object> categorys = new HashMap<>();
        if (!cIds.isEmpty()) {
            List<ArticleCategory> linkCategories = articleCategoryService.listByIds(cIds);
            for (ArticleCategory category : linkCategories) {
                Map<String, Object> c = new HashMap<>();
                c.put("id", category.getId());
                c.put("name", category.getName());
                c.put("alias", category.getAlias());

                categorys.put(category.getId(), c);
            }
        }

        // 设置数据
        List<SearchListVO> listVo= new ArrayList<>();
        for (Search item2 : list.getRecords() ) {
            SearchListVO aListVo = new SearchListVO();
            aListVo.setId(item2.getId());
            aListVo.setType(item2.getType());
            aListVo.setTitle(item2.getTitle());
            aListVo.setDescription(item2.getDescription());
            aListVo.setThumb(item2.getThumb());
            aListVo.setAddtime(item2.getAddtime());
            if (null != item2.getCategoryId() && 0 != item2.getCategoryId()) {
                aListVo.setCategory(categorys.get(item2.getCategoryId()));
            }else {
                aListVo.setCategory(new HashMap<>());
            }

            listVo.add(aListVo);
        }


        searchListVOPage.setRecords(listVo);
        return searchListVOPage;
    }

    @Override
    public IPage<Search> page(IPage<Search> page, SearchSearchDTO searchSearchDTO) {
        QueryWrapper<Search> qw = new QueryWrapper<>();
        if(null != searchSearchDTO.getKeywords()) {
            qw.like("title",searchSearchDTO.getKeywords());
        }

        String sort = searchSearchDTO.getSort() == null ? getSort(0) : getSort(searchSearchDTO.getSort());
        if (null != searchSearchDTO.getOrder() && searchSearchDTO.getOrder() == 1) {
            qw.orderByAsc(sort);
        } else {
            qw.orderByDesc(sort);
        }

        return super.page(page, qw);
    }

    @Override
    public Search articleToSearch(Article entity) {
        Search search = new Search();
        search.setId(entity.getId());
        search.setType(1L);
        search.setCategoryId(entity.getCategoryId());
        search.setTitle(entity.getTitle());
        search.setKeywords(entity.getKeywords());
        search.setDescription(entity.getDescription());
        search.setThumb(entity.getThumb());
        search.setTag(entity.getTag());
        search.setAddtime(entity.getAddtime());
        search.setKeyPrecise(this.getKeyPrecise(entity.getCategoryIds(),entity.getTag()));
        return search;
    }

    @Override
    public Search worksToSearch(Works entity) {
        Search search = new Search();
        search.setId(entity.getId());
        search.setType(2L);
        search.setCategoryId(null);
        search.setTitle(entity.getTitle());
        search.setKeywords(entity.getKeywords());
        search.setDescription(entity.getDescription());
        search.setThumb(entity.getThumb());
        search.setTag(entity.getTag());
        search.setAddtime(entity.getAddtime());
        search.setKeyPrecise(this.getKeyPrecise("",entity.getTag()));
        return search;
    }

    /**
     * 生成 搜索关键词
     *
     * @param categoryIds 分类id字符串,分割
     * @param tag 标签字符串,分割
     */
    private String getKeyPrecise(String categoryIds, String tag) {
        ArrayList<String> keyArr = new ArrayList<>();

        if(null != categoryIds && "" != categoryIds) {
            String[] catArr = categoryIds.split(",");
            for (int i = catArr.length - 1; i >= 0; i--) {
                // 生成 类似 category_1 category_2  category_3 这样搜索没有下面一种搜索精确
                keyArr.add("category_" + catArr[i]);

                // 生成 类似 category_1_0_0 category_1_2_0 category_1_2_3 这样搜索更精确，最好固定分类成绩 如 统一3级或4级
                /*
                String join = StringUtils.join(Arrays.asList(catArr), '_');
                keyArr.add("category_" + join);
                catArr[i] = "0";
                */
            }
        }

        if(null != tag && "" != tag) {
            String[] tagArr = tag.split(",");
            for (int i = tagArr.length - 1; i >= 0; i--) {
                keyArr.add("tag_" + tagArr[i]);
            }
        }

        return StringUtils.join(keyArr, ' ');
    }
}
