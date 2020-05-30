package cc.liqingsong.api.pc.controller.v1;

import cc.liqingsong.api.pc.controller.BaseController;
import cc.liqingsong.common.entity.PageData;
import cc.liqingsong.common.entity.Result;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.pc.ArticleSearchDTO;
import cc.liqingsong.database.entity.ArticleCategory;
import cc.liqingsong.database.vo.pc.ArticleDetailVO;
import cc.liqingsong.database.vo.pc.ArticleListVO;
import cc.liqingsong.service.pc.ArticleCategoryService;
import cc.liqingsong.service.pc.ArticleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 随笔控制器
 * @author liqingsong
 */
@RestController("pcV1ArticleController")
@RequestMapping("/pc/v1")
public class ArticleController  extends BaseController {

    private ArticleCategoryService articleCategoryService;
    @Autowired
    public void setArticleCategoryService(ArticleCategoryService articleCategoryService) {
        this.articleCategoryService = articleCategoryService;
    }

    private ArticleService articleService;
    @Autowired
    private void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 文章分类信息
     */
    @RequestMapping(value = "/article/category", method = RequestMethod.GET)
    public Result articleCategory(String alias) {
        ArticleCategory info = articleCategoryService.selectByAliasAndAddHit(alias);
        if(null == info) {
            return new Result(ResultCode.NOT_FOUND);
        }
        return new Result(ResultCode.SUCCESS,info);
    }

    /**
     * 文章列表
     */
    @RequestMapping(value = "/article/list", method = RequestMethod.GET)
    public Result articleList(ArticleSearchDTO articleSearchDTO) {
        IPage page = articleService.ArticleListVOPage(getPage(), articleSearchDTO);
        return new Result(ResultCode.SUCCESS,new PageData<>(page));
    }

    /**
     * 文章详情
     */
    @RequestMapping(value = "/article/detail", method = RequestMethod.GET)
    public Result articleDetail(Integer id) {
        ArticleDetailVO vo = articleService.ArticleDetailVOByIdAndAddHit(id);
        if(null == vo) {
            return new Result(ResultCode.NOT_FOUND);
        }
        return new Result(ResultCode.SUCCESS,vo);
    }

    /**
     * 文章详情可能感兴趣
     */
    @RequestMapping(value = "/article/interest", method = RequestMethod.GET)
    public Result articleInterest(String ids) {
        List<ArticleListVO> list = articleService.ArticleListVOByIds(ids);
        return new Result(ResultCode.SUCCESS,list);
    }

}
