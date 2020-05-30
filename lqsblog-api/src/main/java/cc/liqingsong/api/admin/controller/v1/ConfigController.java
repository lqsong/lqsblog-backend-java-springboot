package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.api.admin.controller.BaseController;
import cc.liqingsong.common.entity.ResultToken;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.service.admin.ConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 站点配置控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1")
public class ConfigController extends BaseController {

    private ConfigService configService;
    @Autowired
    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * 站点配置详情
     */
    @RequiresPermissions(value = "/admin/v1/config:read")
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public ResultToken configRead() {
        Map all = configService.getAll();
        return new ResultToken(ResultCode.SUCCESS, all, this.newToken);
    }
    /*
    public ResultToken configRead() throws IllegalAccessException {
        ConfigDTO configDTO = configService.getConfigDTO();
        return new ResultToken(ResultCode.SUCCESS, configDTO, this.newToken);
    }
    */

    /**
     * 站点配置添加(修改)
     */
    @RequiresPermissions(value = "/admin/v1/config:update")
    @RequestMapping(value = "/config", method = RequestMethod.POST)
    public ResultToken configCreate(@RequestBody Map<String,String> map) {
        boolean b = configService.updateAll(map);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }
    /*
    public ResultToken configCreate(@RequestBody ConfigDTO configDTO) throws IllegalAccessException {
        boolean b = configService.updateConfigDTO(configDTO);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }
    */

}
