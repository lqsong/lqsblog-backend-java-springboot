package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.api.admin.controller.BaseController;
import cc.liqingsong.common.entity.ResultToken;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.SinglePage;
import cc.liqingsong.service.admin.SinglePageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关于控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1")
public class AboutController  extends BaseController {

    private SinglePageService singlePageService;
    @Autowired
    public void setSinglePageService(SinglePageService singlePageService) {
        this.singlePageService = singlePageService;
    }

    /**
     * 关于我详情
     */
    @RequiresPermissions(value = "/admin/v1/about:read")
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public ResultToken aboutRead() {
        SinglePage info = singlePageService.getById(1);
        return new ResultToken(ResultCode.SUCCESS, info, this.newToken);
    }

    /**
     * 关于我添加(修改)
     */
    @RequiresPermissions(value = "/admin/v1/about:update")
    @RequestMapping(value = "/about", method = RequestMethod.POST)
    public ResultToken aboutCreate(@RequestBody SinglePage singlePage) {
        singlePage.setId(1L);
        boolean b = singlePageService.updateById(singlePage);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }


}
