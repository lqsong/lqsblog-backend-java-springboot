package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.api.admin.controller.BaseController;
import cc.liqingsong.common.entity.ResultToken;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.vo.admin.ArticleDailyNewVO;
import cc.liqingsong.database.vo.admin.StatsTotalChartVO;
import cc.liqingsong.service.admin.ArticleService;
import cc.liqingsong.service.admin.LinkService;
import cc.liqingsong.service.admin.TopicsService;
import cc.liqingsong.service.admin.WorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统计控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1")
public class StatsController  extends BaseController {

    private ArticleService articleService;
    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    private WorksService worksService;
    @Autowired
    public void setWorksService(WorksService worksService) {
        this.worksService = worksService;
    }

    private TopicsService topicsService;
    @Autowired
    public void setTopicsService(TopicsService topicsService) {
        this.topicsService = topicsService;
    }

    private LinkService linkService;
    @Autowired
    public void setLinkService(LinkService linkService) {
        this.linkService = linkService;
    }

    /**
     * 随笔 - 日新增，总量，日同比，周同比
     */
    @RequestMapping(value = "/stats/articles/dailynew", method = RequestMethod.GET)
    public ResultToken articlesDailyNew() {
        ArticleDailyNewVO vo = articleService.getArticleDailyNewVO();
        return new ResultToken(ResultCode.SUCCESS, vo, this.newToken);
    }


    /**
     * 作品 - 周新增，总量，chart数据
     */
    @RequestMapping(value = "/stats/works/weeknew", method = RequestMethod.GET)
    public ResultToken worksWeekNew() {
        StatsTotalChartVO vo = worksService.getStatsTotalChartVO();
        return new ResultToken(ResultCode.SUCCESS, vo, this.newToken);
    }

    /**
     * 专题 - 月新增，总量，chart数据
     */
    @RequestMapping(value = "/stats/topics/monthnew", method = RequestMethod.GET)
    public ResultToken topicsMonthNew() {
        StatsTotalChartVO vo = topicsService.getStatsTotalChartVO();
        return new ResultToken(ResultCode.SUCCESS, vo, this.newToken);
    }


    /**
     * 左邻右舍 - 年新增，总量，chart数据
     */
    @RequestMapping(value = "/stats/links/annualnew", method = RequestMethod.GET)
    public ResultToken linksAnnualNew() {
        StatsTotalChartVO vo = linkService.getStatsTotalChartVO();
        return new ResultToken(ResultCode.SUCCESS, vo, this.newToken);
    }



}
