package cc.liqingsong.api.pc.controller.v1;

import cc.liqingsong.api.pc.controller.BaseController;
import cc.liqingsong.common.entity.Result;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.vo.pc.LinkCategoryVO;
import cc.liqingsong.database.vo.pc.LinkVO;
import cc.liqingsong.service.pc.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 左邻右舍控制器
 * @author liqingsong
 */
@RestController("pcV1LinkController")
@RequestMapping("/pc/v1")
public class LinkController extends BaseController {

    private LinkService linkService;
    @Autowired
    public void setLinkService(LinkService linkService) {
        this.linkService = linkService;
    }

    /**
     * 左邻右舍列表
     */
    @RequestMapping(value = "/links/list", method = RequestMethod.GET)
    public Result linksList() {
        List<LinkCategoryVO> vo = linkService.selectLinkCategoryVOAll();
        return new Result(ResultCode.SUCCESS,vo);
    }

    /**
     * 左邻右舍推荐
     */
    @RequestMapping(value = "/links/recommend", method = RequestMethod.GET)
    public Result linksRecommend(String ids) {
        if(null == ids) {
            return new Result(ResultCode.SUCCESS,new ArrayList<>());
        }
        String[] split = ids.split(",");
        List<LinkVO> vo = linkService.getByCategoryId(Arrays.asList(split));
        return new Result(ResultCode.SUCCESS,vo);
    }

}
