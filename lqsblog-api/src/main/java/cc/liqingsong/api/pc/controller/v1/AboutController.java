package cc.liqingsong.api.pc.controller.v1;

import cc.liqingsong.api.pc.controller.BaseController;
import cc.liqingsong.common.entity.Result;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.SinglePage;
import cc.liqingsong.service.pc.SinglePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关于控制器
 * @author liqingsong
 */
@RestController("pcV1AboutController")
@RequestMapping("/pc/v1")
public class AboutController extends BaseController {

    private SinglePageService singlePageService;
    @Autowired
    public void setSinglePageService(SinglePageService singlePageService) {
        this.singlePageService = singlePageService;
    }

    /**
     * 关于我详情
     */
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public Result aboutRead() {
        SinglePage info = singlePageService.getByIdAndAddHit(1);
        return new Result(ResultCode.SUCCESS,info);
    }

}
