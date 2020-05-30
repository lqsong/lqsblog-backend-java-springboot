package cc.liqingsong.service.pc.Impl;

import cc.liqingsong.database.dto.pc.ArticleSearchDTO;
import cc.liqingsong.database.entity.Article;
import cc.liqingsong.database.entity.ArticleCategory;
import cc.liqingsong.database.mapper.ArticleMapper;
import cc.liqingsong.database.vo.pc.ArticleDetailVO;
import cc.liqingsong.database.vo.pc.ArticleListVO;
import cc.liqingsong.service.pc.ArticleCategoryService;
import cc.liqingsong.service.pc.ArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * Article 服务实现类
 *
 * @author liqingsong
 */
@Service("pcArticleServiceImpl")
public class ArticleServiceImpl  extends ServiceImpl<ArticleMapper, Article> implements ArticleService {


    private ArticleCategoryService articleCategoryService;
    @Autowired
    public void setArticleCategoryService(ArticleCategoryService articleCategoryService) {
        this.articleCategoryService = articleCategoryService;
    }

    /**
     * 排序字段
     */
    private String[] sort = new String[]{"addtime","id","hit"};
    public String getSort(int i ) {
        int length = sort.length - 1;
        return i > length ? sort[0] : sort[i];
    }



    @Override
    public IPage<ArticleListVO> ArticleListVOPage(IPage<Article> page, ArticleSearchDTO articleSearchDTO) {
        // 读取分页数据
        IPage<Article> list = page(page, articleSearchDTO);

        // 设置返回数据
        Page<ArticleListVO> articleListVOPage = new Page<>();
        articleListVOPage.setCurrent(list.getCurrent());
        articleListVOPage.setTotal(list.getTotal());
        articleListVOPage.setPages(list.getPages());
        articleListVOPage.setSize(list.getSize());
        articleListVOPage.setOrders(list.orders());
        articleListVOPage.setOptimizeCountSql(list.optimizeCountSql());
        articleListVOPage.setSearchCount(list.isSearchCount());

        if(list.getRecords().isEmpty()) {
            articleListVOPage.setRecords(new ArrayList<>());
            return articleListVOPage;
        }

        // 取出分类id
        List<Long> cIds = new ArrayList<>();
        for (Article item : list.getRecords() ) {
            cIds.add(item.getCategoryId());
        }

        // 设置分类
        Map<Long, Object> categorys = new HashMap<>();
        List<ArticleCategory> articleCategories = articleCategoryService.listByIds(cIds);
        for ( ArticleCategory category: articleCategories ) {
            Map<String, Object> c = new HashMap<>();
            c.put("id",category.getId());
            c.put("name",category.getName());
            c.put("alias",category.getAlias());

            categorys.put(category.getId(),c);
        }




        // 设置数据
        List<ArticleListVO> listVo= new ArrayList<>();
        for (Article item2 : list.getRecords() ) {
            ArticleListVO aListVo = new ArticleListVO();
            aListVo.setId(item2.getId());
            aListVo.setTitle(item2.getTitle());
            aListVo.setDescription(item2.getDescription());
            aListVo.setThumb(Arrays.asList(item2.getThumb().split("\\|")));
            aListVo.setAddtime(item2.getAddtime());
            aListVo.setCategory(categorys.get(item2.getCategoryId()));
            aListVo.setTag(item2.getTag());
            aListVo.setHit(item2.getHit());
            listVo.add(aListVo);
        }


        articleListVOPage.setRecords(listVo);
        return articleListVOPage;
    }

    @Override
    public IPage<Article> page(IPage<Article> page, ArticleSearchDTO articleSearchDTO) {
        QueryWrapper<Article> qw = new QueryWrapper<>();
        if(null != articleSearchDTO.getCategoryId()) {
            qw.eq("category_id",articleSearchDTO.getCategoryId());
        }

        String sort = articleSearchDTO.getSort() == null ? getSort(0) : getSort(articleSearchDTO.getSort());
        if (null != articleSearchDTO.getOrder() && articleSearchDTO.getOrder() == 1) {
            qw.orderByAsc(sort);
        } else {
            qw.orderByDesc(sort);
        }

        return super.page(page, qw);
    }

    @Override
    public ArticleDetailVO ArticleDetailVOById(Serializable id) {

        Article info = super.getById(id);
        if(null == info) {
            return null;
        }

        ArticleCategory category = articleCategoryService.getById(info.getCategoryId());
        Map<String, Object> c = new HashMap<>();
        c.put("id",category.getId());
        c.put("name",category.getName());
        c.put("alias",category.getAlias());

        ArticleDetailVO vo = new ArticleDetailVO();
        vo.setId(info.getId());
        vo.setTitle(info.getTitle());
        vo.setKeywords(info.getKeywords());
        vo.setDescription(info.getDescription());
        vo.setAddtime(info.getAddtime());
        vo.setCategory(c);
        vo.setTag(Arrays.asList(info.getTag().split(",")));
        vo.setContent(info.getContent());
        vo.setInterestIds(info.getInterestIds());
        vo.setHit(info.getHit());


        return vo;
    }

    @Override
    public ArticleDetailVO ArticleDetailVOByIdAndAddHit(Serializable id) {
        ArticleDetailVO articleDetailVO = ArticleDetailVOById(id);
        if (null == articleDetailVO) {
            return null;
        }

        Article article = new Article();
        article.setId(articleDetailVO.getId());
        // 浏览量 + 1
        article.setHit(articleDetailVO.getHit() + 1);

        this.updateById(article);

        return articleDetailVO;
    }

    @Override
    public List<ArticleListVO> ArticleListVOByIds(String ids) {
        QueryWrapper<Article> qw = new QueryWrapper<>();
        qw.inSql("id", ids);
        List<Article> list = super.list(qw);


        List<ArticleListVO> articleListVOS = new ArrayList<>();

        if(list.isEmpty()) {
            return articleListVOS;
        }

        // 取出分类id
        List<Long> cIds = new ArrayList<>();
        for (Article item : list ) {
            cIds.add(item.getCategoryId());
        }

        // 设置分类
        Map<Long, Object> categorys = new HashMap<>();
        List<ArticleCategory> articleCategories = articleCategoryService.listByIds(cIds);
        for ( ArticleCategory category: articleCategories ) {
            Map<String, Object> c = new HashMap<>();
            c.put("id",category.getId());
            c.put("name",category.getName());
            c.put("alias",category.getAlias());

            categorys.put(category.getId(),c);
        }


        for (Article item2 : list ) {
            ArticleListVO aListVo = new ArticleListVO();
            aListVo.setId(item2.getId());
            aListVo.setTitle(item2.getTitle());
            aListVo.setDescription(item2.getDescription());
            aListVo.setThumb(Arrays.asList(item2.getThumb().split("\\|")));
            aListVo.setAddtime(item2.getAddtime());
            aListVo.setCategory(categorys.get(item2.getCategoryId()));
            aListVo.setTag(item2.getTag());
            aListVo.setHit(item2.getHit());

            articleListVOS.add(aListVo);
        }


        return articleListVOS;
    }
}
