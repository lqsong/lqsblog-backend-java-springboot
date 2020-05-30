package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.api.admin.controller.BaseController;
import cc.liqingsong.common.entity.PageData;
import cc.liqingsong.common.entity.ResultToken;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.admin.ArticleSearchDTO;
import cc.liqingsong.database.entity.Article;
import cc.liqingsong.database.entity.ArticleCategory;
import cc.liqingsong.database.vo.admin.CascaderVO;
import cc.liqingsong.database.vo.admin.ArticleInterestVO;
import cc.liqingsong.database.vo.admin.SaveIdVO;
import cc.liqingsong.service.admin.ArticleCategoryService;
import cc.liqingsong.service.admin.ArticleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1")
public class ArticleController extends BaseController {

    private ArticleCategoryService articleCategoryService;
    @Autowired
    public void setArticleCategoryService(ArticleCategoryService articleCategoryService) {
        this.articleCategoryService = articleCategoryService;
    }

    private ArticleService articleService;
    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 文章列表
     */
    @RequiresPermissions(value = "/admin/v1/articles:list")
    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public ResultToken articleList(ArticleSearchDTO articleSearchDTO) {
        IPage page = articleService.listVOPage(getPage(), articleSearchDTO);
        return new ResultToken(ResultCode.SUCCESS, new PageData<>(page), this.newToken);
    }

    /**
     * 文章添加
     */
    @RequiresPermissions(value = "/admin/v1/articles:create")
    @RequestMapping(value = "/articles", method = RequestMethod.POST)
    public ResultToken articleCreate(@RequestBody Article article) {
        article.setCreatorId(this.userId);
        boolean save = articleService.save(article);
        return save ? new ResultToken(ResultCode.SUCCESS, new SaveIdVO().setId(article.getId()),this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 文章编辑
     */
    @RequiresPermissions(value = "/admin/v1/articles:update")
    @RequestMapping(value = "/articles/{id}", method = RequestMethod.PUT)
    public ResultToken articleUpdate(@PathVariable Long id,  @RequestBody Article article ) {
        article.setId(id);
        boolean b = articleService.updateById(article);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 文章删除
     */
    @RequiresPermissions(value = "/admin/v1/articles:delete")
    @RequestMapping(value = "/articles/{id}", method = RequestMethod.DELETE)
    public ResultToken articleDelete(@PathVariable Long id) {
        boolean b = articleService.removeById(id);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 文章详情
     */
    @RequiresPermissions(value = "/admin/v1/articles:read")
    @RequestMapping(value = "/articles/{id}", method = RequestMethod.GET)
    public ResultToken articleRead(@PathVariable Long id) {
        ArticleInterestVO article = articleService.getArticleInterestVOById(id);
        return new ResultToken(ResultCode.SUCCESS, article, this.newToken);
    }

    /**
     * 文章分类列表
     */
    @RequiresPermissions(value = "/admin/v1/articles/categorys:list")
    @RequestMapping(value = "/article/categorys", method = RequestMethod.GET)
    public ResultToken categoryList(@RequestParam Long pid) {
        List<ArticleCategory> articleCategories = articleCategoryService.listByPid(pid);
        return new ResultToken(ResultCode.SUCCESS, articleCategories, this.newToken);
    }

    /**
     * 文章分类添加
     */
    @RequiresPermissions(value = "/admin/v1/articles/categorys:create")
    @RequestMapping(value = "/article/categorys", method = RequestMethod.POST)
    public ResultToken categoryCreate(@RequestBody ArticleCategory articleCategory) {
        boolean save = articleCategoryService.save(articleCategory);
        return save ? new ResultToken(ResultCode.SUCCESS, new SaveIdVO().setId(articleCategory.getId()),this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 文章分类编辑
     */
    @RequiresPermissions(value = "/admin/v1/articles/categorys:update")
    @RequestMapping(value = "/article/categorys/{id}", method = RequestMethod.PUT)
    public ResultToken categoryUpdate(@PathVariable Long id, @RequestBody ArticleCategory articleCategory ) {
        articleCategory.setId(id);
        boolean b = articleCategoryService.updateById(articleCategory);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 文章分类删除
     */
    @RequiresPermissions(value = "/admin/v1/articles/categorys:delete")
    @RequestMapping(value = "/article/categorys/{id}", method = RequestMethod.DELETE)
    public ResultToken categoryDelete(@PathVariable Long id) {
        boolean b = articleCategoryService.removeById(id);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 文章分类联动下拉
     */
    // @RequiresPermissions(value = "/admin/v1/articles/categorys/cascader")
    @RequestMapping(value = "/article/categorys/cascader", method = RequestMethod.GET)
    public ResultToken categoryCascader(@RequestParam Long pid) {
        List<CascaderVO> list = articleCategoryService.selectCascaderVO(pid);
        return new ResultToken(ResultCode.SUCCESS, list, this.newToken);
    }



}
