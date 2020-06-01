package cc.liqingsong.api.pc.controller.v1;

import cc.liqingsong.api.pc.controller.BaseController;
import cc.liqingsong.common.entity.PageData;
import cc.liqingsong.common.entity.Result;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.common.utils.IpUtil;
import cc.liqingsong.database.dto.pc.TopicsSearchDTO;
import cc.liqingsong.database.entity.Topics;
import cc.liqingsong.database.vo.pc.TopicsDetailVO;
import cc.liqingsong.service.pc.TopicsLogService;
import cc.liqingsong.service.pc.TopicsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 专题控制器
 * @author liqingsong
 */
@RestController("pcV1TopicsController")
@RequestMapping("/pc/v1")
public class TopicsController extends BaseController {

    private TopicsService topicsService;
    @Autowired
    public void setTopicsService(TopicsService topicsService) {
        this.topicsService = topicsService;
    }

    private TopicsLogService topicsLogService;
    @Autowired
    public void setTopicsLogService(TopicsLogService topicsLogService) {
        this.topicsLogService = topicsLogService;
    }

    /**
     * 专题列表
     */
    @RequestMapping(value = "/topics/list", method = RequestMethod.GET)
    public Result topicsList(TopicsSearchDTO topicsSearchDTO) {
        IPage page = topicsService.TopicsListVOPage(getPage(), topicsSearchDTO);
        return new Result(ResultCode.SUCCESS,new PageData<>(page));
    }

    /**
     * 专题详情
     */
    @RequestMapping(value = "/topics/detail", method = RequestMethod.GET)
    public Result topicsDetail(String alias) {
        TopicsDetailVO vo = topicsService.TopicsDetailVOByAliasAndAddHit(alias);
        if(null == vo) {
            return new Result(ResultCode.NOT_FOUND);
        }

        // 添加Log
        String ipAddr = IpUtil.getIpAddr(this.request);
        Topics topics = new Topics();
        topics.setId(vo.getId());
        topics.setTitle(vo.getTitle());
        topicsLogService.saveTopicsIp(topics,ipAddr);

        return new Result(ResultCode.SUCCESS, vo);
    }


}
