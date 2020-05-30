package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.api.admin.controller.BaseController;
import cc.liqingsong.common.entity.ResultToken;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.Role;
import cc.liqingsong.database.vo.admin.SaveIdVO;
import cc.liqingsong.service.admin.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 角色控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1")
public class RoleController extends BaseController {

    private RoleService roleService;
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    /**
     * 角色列表
     */
    @RequiresPermissions(value = "/admin/v1/roles:list")
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public ResultToken roleList() {
        List<Role> list = roleService.list();
        return new ResultToken(ResultCode.SUCCESS, list, this.newToken);
    }

    /**
     * 角色添加
     */
    @RequiresPermissions(value = "/admin/v1/roles:create")
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public ResultToken roleCreate(@RequestBody Role role) {
        boolean save = roleService.save(role);
        return save ? new ResultToken(ResultCode.SUCCESS, new SaveIdVO().setId(role.getId()),this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 角色编辑
     */
    @RequiresPermissions(value = "/admin/v1/roles:update")
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.PUT)
    public ResultToken roleUpdate(@PathVariable Long id, @RequestBody Role role) {
        role.setId(id);
        boolean b = roleService.updateById(role);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 角色删除
     */
    @RequiresPermissions(value = "/admin/v1/roles:delete")
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
    public ResultToken roleDelete(@PathVariable Long id) {
        boolean b = roleService.removeById(id);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }




}
