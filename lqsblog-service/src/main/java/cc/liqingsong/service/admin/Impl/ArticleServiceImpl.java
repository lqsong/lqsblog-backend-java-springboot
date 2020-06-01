package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.admin.ArticleSearchDTO;
import cc.liqingsong.database.entity.Article;
import cc.liqingsong.database.entity.ArticleCategory;
import cc.liqingsong.database.enums.ArticleCode;
import cc.liqingsong.database.mapper.ArticleMapper;
import cc.liqingsong.database.vo.admin.ArticleDailyNewVO;
import cc.liqingsong.database.vo.admin.ArticleInterestVO;
import cc.liqingsong.database.vo.admin.ArticleListVO;
import cc.liqingsong.database.vo.admin.ArticleSimplifyVO;
import cc.liqingsong.service.admin.ArticleCategoryService;
import cc.liqingsong.service.admin.ArticleService;
import cc.liqingsong.service.admin.SearchService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Article 服务实现类
 *
 * @author liqingsong
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private SearchService searchService;
    @Autowired
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    private ArticleCategoryService articleCategoryService;
    @Autowired
    public void setArticleCategoryService(ArticleCategoryService articleCategoryService) {
        this.articleCategoryService = articleCategoryService;
    }


    /**
     * 排序字段
     */
    private String[] sort = new String[]{"id","hit","addtime"};
    public String getSort(int i ) {
        int length = sort.length - 1;
        return i > length ? sort[0] : sort[i];
    }


    /**
     * 插入一条记录
     *
     * @param entity 实体类对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(Article entity) {
        if (null == entity) {
            return false;
        }

        saveUpdateVerify(entity);

        entity.setCreateTime(LocalDateTime.now());

        return super.save(entity) && searchService.save(searchService.articleToSearch(entity));
    }

    /**
     * 根据 ID 删除
     *
     * @param id ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {

        Article article = new Article();
        article.setId((Long) id);
        boolean remove = searchService.remove(searchService.articleToSearch(article));

        return super.removeById(id);
    }

    /**
     * 根据 ID 修改
     *
     * @param entity 实体类对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(Article entity) {

        if (null == entity) {
            return false;
        }

        Assert.fail(null == entity.getId(), ResultCode.ID_NOT_FOUND);

        saveUpdateVerify(entity);

        return super.updateById(entity) && searchService.updateById(searchService.articleToSearch(entity));
    }





    /**
     * 私用验证
     *
     * @param entity 实体类对象
     */
    private void saveUpdateVerify(Article entity) {

        Assert.fail(null == entity.getTitle(), ResultCode.TITLE_NOT_EMPTY);
        int titleLen = entity.getTitle().length();
        Assert.fail(titleLen > 100 || titleLen < 3, ArticleCode.TITLE_LENGTH_WORDS);

        Assert.fail(null == entity.getCategoryId(), ArticleCode.CATEGORY_ID_NOT_EMPTY);
        Assert.fail(null == entity.getCategoryIds(), ArticleCode.CATEGORY_ID_NOT_EMPTY);

        Assert.fail(null == entity.getAddtime() || StringUtils.isEmpty(entity.getAddtime()), ArticleCode.ADDTIME_NOT_EMPTY);

        Assert.fail(null == entity.getKeywords(), ResultCode.INCORRECT_PARAMETER);
        int keyLen = entity.getKeywords().length();
        Assert.fail(keyLen > 50, ResultCode.KEYWORDS_LENGTH_WORDS);

        Assert.fail(null == entity.getDescription(), ResultCode.INCORRECT_PARAMETER);
        int desLen = entity.getDescription().length();
        Assert.fail(desLen > 200, ResultCode.DESCRIPTION_LENGTH_WORDS);


        Assert.fail(null == entity.getThumb(), ResultCode.INCORRECT_PARAMETER);
        Assert.fail(null == entity.getContent(), ResultCode.INCORRECT_PARAMETER);
        Assert.fail(null == entity.getTag(), ResultCode.INCORRECT_PARAMETER);
        Assert.fail(null == entity.getInterestIds(), ResultCode.INCORRECT_PARAMETER);



    }


    @Override
    public IPage<ArticleListVO> listVOPage(IPage<Article> page, ArticleSearchDTO articleSearchDTO) {
        // 读取文章分页数据
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
            aListVo.setCategory(categorys.get(item2.getCategoryId()));
            aListVo.setCategoryIds(item2.getCategoryIds());
            aListVo.setAddtime(item2.getAddtime());
            aListVo.setHit(item2.getHit());
            aListVo.setTag(item2.getTag());
            listVo.add(aListVo);
        }


        articleListVOPage.setRecords(listVo);
        return articleListVOPage;
    }

    @Override
    public IPage<Article> page(IPage<Article> page, ArticleSearchDTO articleSearchDTO) {
        QueryWrapper<Article> qw = new QueryWrapper<>();
        if(null != articleSearchDTO.getKeywords()) {
            qw.like("title",articleSearchDTO.getKeywords());
        }

        if(null != articleSearchDTO.getCategoryid()) {
            qw.eq("category_id",articleSearchDTO.getCategoryid());
        }

        if(null != articleSearchDTO.getAddtimeend() && "" != articleSearchDTO.getAddtimeend() && null != articleSearchDTO.getAddtimestart() && "" != articleSearchDTO.getAddtimestart()) {
            qw.between("addtime",articleSearchDTO.getAddtimestart(),articleSearchDTO.getAddtimeend());
        }

        if(null != articleSearchDTO.getTags()) {
            qw.like("tag", articleSearchDTO.getTags());
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
    public ArticleInterestVO getArticleInterestVOById(Long id) {

        Article article = super.getById(id);

        String[] split = article.getInterestIds().split(",");
        List<ArticleSimplifyVO> articleSimplifyVOS = baseMapper.selectSimplifyVO(Arrays.asList(split));

        ArticleInterestVO articleInterestVO = new ArticleInterestVO();
        articleInterestVO.setId(article.getId());
        articleInterestVO.setCategoryId(article.getCategoryId());
        articleInterestVO.setCategoryIds(article.getCategoryIds());
        articleInterestVO.setTitle(article.getTitle());
        articleInterestVO.setKeywords(article.getKeywords());
        articleInterestVO.setDescription(article.getDescription());
        articleInterestVO.setThumb(article.getThumb());
        articleInterestVO.setContent(article.getContent());
        articleInterestVO.setTag(article.getTag());
        articleInterestVO.setInterestIds(article.getInterestIds());
        articleInterestVO.setAddtime(article.getAddtime());
        articleInterestVO.setHit(article.getHit());
        articleInterestVO.setCreatorId(article.getCreatorId());
        articleInterestVO.setCreateTime(article.getCreateTime());
        articleInterestVO.setInterest(articleSimplifyVOS);

        return articleInterestVO;
    }

    @Override
    public ArticleDailyNewVO getArticleDailyNewVO() {
        // 当前日期
        LocalDate now = LocalDate.now();
        // 前一天日期
        LocalDate dayBefore = now.plusDays(-1);
        // 后一天日期
        LocalDate dayAfter = now.plusDays(1);

        // 获取本周几
        int weekValue = now.getDayOfWeek().getValue();
        // 获取上周一日期
        LocalDate LastMonday = now.minusDays(7 + weekValue - 1);
        // 获取上周日日期
        // LocalDate lastSunday = now.minusDays(weekValue);
        // 获取本周一日期
        LocalDate monday = now.minusDays(weekValue - 1);

        // 总文章数
        int total = super.count();

        // 今天新增文章
        QueryWrapper<Article> numQw = new QueryWrapper<>();
        numQw.ge("create_time",now);
        numQw.lt("create_time",dayAfter);
        int num = super.count(numQw);

        // 前一天新增文章
        QueryWrapper<Article> numQwBefore = new QueryWrapper<>();
        numQwBefore.ge("create_time",dayBefore);
        numQwBefore.lt("create_time",now);
        int numBefore = super.count(numQwBefore);

        // 日同比%
        double dayCompare = numBefore > 0 ? (double)Math.round((double) (num - numBefore) / (double) numBefore * 10000)/100 : num * 100;

        // 本周新增文章
        QueryWrapper<Article> weekQw = new QueryWrapper<>();
        weekQw.ge("create_time",monday);
        weekQw.lt("create_time",dayAfter);
        int week = super.count(weekQw);

        // 上周新增文章
        QueryWrapper<Article> weekQwBefore = new QueryWrapper<>();
        weekQwBefore.ge("create_time",LastMonday);
        weekQwBefore.lt("create_time",monday);
        int weekBefore = super.count(weekQwBefore);

        // 周同比%
        double weekCompare = weekBefore > 0 ? (double)Math.round((double) (week - weekBefore) / (double) weekBefore * 10000)/100 : week * 100;

        ArticleDailyNewVO vo = new ArticleDailyNewVO();
        vo.setTotal(total);
        vo.setNum(num);
        vo.setDay(dayCompare);
        vo.setWeek(weekCompare);

        return vo;
    }
}
