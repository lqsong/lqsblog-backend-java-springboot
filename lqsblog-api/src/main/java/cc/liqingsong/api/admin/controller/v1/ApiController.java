package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.api.admin.controller.BaseController;
import cc.liqingsong.common.entity.ResultToken;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.Permission;
import cc.liqingsong.database.vo.admin.CascaderVO;
import cc.liqingsong.database.vo.admin.IdNamePidVO;
import cc.liqingsong.database.vo.admin.SaveIdVO;
import cc.liqingsong.service.admin.PermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * API控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1")
public class ApiController extends BaseController {

    private PermissionService permissionService;
    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }


    /**
     * API列表
     */
    @RequiresPermissions(value = "/admin/v1/apis:list")
    @RequestMapping(value = "/apis", method = RequestMethod.GET)
    public ResultToken apiList(@RequestParam Long pid) {
        List<Permission> permissions = permissionService.listByPid(pid);
        return new ResultToken(ResultCode.SUCCESS, permissions, this.newToken);
    }

    /**
     * API添加
     */
    @RequiresPermissions(value = "/admin/v1/apis:create")
    @RequestMapping(value = "/apis", method = RequestMethod.POST)
    public ResultToken apiCreate(@RequestBody Permission permission) {
        boolean save = permissionService.save(permission);
        return save ? new ResultToken(ResultCode.SUCCESS, new SaveIdVO().setId(permission.getId()),this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * API编辑
     */
    @RequiresPermissions(value = "/admin/v1/apis:update")
    @RequestMapping(value = "/apis/{id}", method = RequestMethod.PUT)
    public ResultToken apiUpdate(@PathVariable Long id,@RequestBody Permission permission) {
        permission.setId(id);
        boolean b = permissionService.updateById(permission);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * API删除
     */
    @RequiresPermissions(value = "/admin/v1/apis:delete")
    @RequestMapping(value = "/apis/{id}", method = RequestMethod.DELETE)
    public ResultToken apiDelete(@PathVariable Long id) {
        boolean b = permissionService.removeById(id);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * API联动下拉
     */
    // @RequiresPermissions(value = "/admin/v1/apis/cascader")
    @RequestMapping(value = "/apis/cascader", method = RequestMethod.GET)
    public ResultToken apiCascader(@RequestParam Long pid) {
        List<CascaderVO> list = permissionService.selectCascaderVO(pid);
        return new ResultToken(ResultCode.SUCCESS, list, this.newToken);
    }

    /**
     * API列表 - 全部
     */
    // @RequiresPermissions(value = "/admin/v1/apis/all:list")
    @RequestMapping(value = "/apis/all", method = RequestMethod.GET)
    public ResultToken apiListAll() {
        List<IdNamePidVO> list = permissionService.selectIdNamePidVO();
        return new ResultToken(ResultCode.SUCCESS, list, this.newToken);
    }



}
