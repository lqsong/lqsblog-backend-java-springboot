package cc.liqingsong.api.pc.controller.v1;


import cc.liqingsong.api.pc.controller.BaseController;
import cc.liqingsong.common.entity.Result;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.service.pc.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 站点配置控制器
 * @author liqingsong
 */
@RestController("pcV1ConfigController")
@RequestMapping("/pc/v1")
public class ConfigController extends BaseController {

    private ConfigService configService;
    @Autowired
    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * 站点配置详情
     */
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public Result configRead() {
        Map<String, String> all = configService.getAll();
        return new Result(ResultCode.SUCCESS,all);
    }




}
