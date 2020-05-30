package cc.liqingsong.service.pc.Impl;

import cc.liqingsong.database.dto.pc.SearchSearchDTO;
import cc.liqingsong.database.entity.ArticleCategory;
import cc.liqingsong.database.entity.Search;
import cc.liqingsong.database.mapper.SearchMapper;
import cc.liqingsong.database.vo.pc.SearchListVO;
import cc.liqingsong.database.vo.pc.SearchRecommendVO;
import cc.liqingsong.service.pc.ArticleCategoryService;
import cc.liqingsong.service.pc.SearchService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Search 服务实现类
 *
 * @author liqingsong
 */
@Service("pcSearchServiceImpl")
public class SearchServiceImpl  extends ServiceImpl<SearchMapper, Search> implements SearchService {

    private ArticleCategoryService articleCategoryService;
    @Autowired
    public void setArticleCategoryService(ArticleCategoryService articleCategoryService){
        this.articleCategoryService = articleCategoryService;
    }
    /**
     * 排序字段
     */
    private String[] sort = new String[]{"addtime","sid"};
    public String getSort(int i ) {
        int length = sort.length - 1;
        return i > length ? sort[0] : sort[i];
    }


    @Override
    public List<SearchRecommendVO> getSearchRecommendVO(Long num) {
        return baseMapper.selectSearchRecommendVO(num);
    }

    @Override
    public IPage<SearchListVO> listVOPage(IPage<Search> page, SearchSearchDTO searchSearchDTO) {
        // 读取分页数据
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
            aListVo.setThumb(Arrays.asList(item2.getThumb().split("\\|")));
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

        if(null != searchSearchDTO.getNoSid() && !searchSearchDTO.getNoSid().isEmpty()) {
            qw.notIn("sid", searchSearchDTO.getNoSid());
        }

        List<String> againstArr = new ArrayList<>();
        if(null != searchSearchDTO.getCategoryId() && 0 != searchSearchDTO.getCategoryId()) {
            againstArr.add("+category_" + searchSearchDTO.getCategoryId());
        }

        if(null != searchSearchDTO.getTag() && !searchSearchDTO.getTag().isEmpty()) {
            againstArr.add("+tag_" + searchSearchDTO.getTag());
        }

        if(!againstArr.isEmpty()) {
            String join = StringUtils.join(againstArr, ' ');
            qw.apply("match(key_precise) against ('" + join + "' IN BOOLEAN MODE) ");
        }


        String sort = searchSearchDTO.getSort() == null ? getSort(0) : getSort(searchSearchDTO.getSort());
        if (null != searchSearchDTO.getOrder() && searchSearchDTO.getOrder() == 1) {
            qw.orderByAsc(sort);
        } else {
            qw.orderByDesc(sort);
        }

        return super.page(page, qw);
    }
}
