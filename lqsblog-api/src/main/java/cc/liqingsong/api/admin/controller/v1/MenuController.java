package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.api.admin.controller.BaseController;
import cc.liqingsong.common.entity.ResultToken;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.Resource;
import cc.liqingsong.database.vo.admin.CascaderVO;
import cc.liqingsong.database.vo.admin.IdNamePidVO;
import cc.liqingsong.database.vo.admin.SaveIdVO;
import cc.liqingsong.service.admin.ResourceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 菜单控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1")
public class MenuController extends BaseController {

    private ResourceService resourceService;
    @Autowired
    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }
    /**
     * 菜单列表
     */
    @RequiresPermissions(value = "/admin/v1/menus:list")
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public ResultToken menuList(@RequestParam Long pid) {
        List<Resource> resources = resourceService.listByPid(pid);
        return new ResultToken(ResultCode.SUCCESS, resources, this.newToken);
    }

    /**
     * 菜单添加
     */
    @RequiresPermissions(value = "/admin/v1/menus:create")
    @RequestMapping(value = "/menus", method = RequestMethod.POST)
    public ResultToken menuCreate(@RequestBody Resource resource) {
        boolean save = resourceService.save(resource);
        return save ? new ResultToken(ResultCode.SUCCESS, new SaveIdVO().setId(resource.getId()),this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 菜单编辑
     */
    @RequiresPermissions(value = "/admin/v1/menus:update")
    @RequestMapping(value = "/menus/{id}", method = RequestMethod.PUT)
    public ResultToken menuUpdate(@PathVariable Long id,@RequestBody Resource resource) {
        resource.setId(id);
        boolean b = resourceService.updateById(resource);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 菜单删除
     */
    @RequiresPermissions(value = "/admin/v1/menus:delete")
    @RequestMapping(value = "/menus/{id}", method = RequestMethod.DELETE)
    public ResultToken menuDelete(@PathVariable Long id) {
        boolean b = resourceService.removeById(id);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 菜单联动下拉
     */
    // @RequiresPermissions(value = "/admin/v1/menus/cascader")
    @RequestMapping(value = "/menus/cascader", method = RequestMethod.GET)
    public ResultToken menuCascader(@RequestParam Long pid) {
        List<CascaderVO> list = resourceService.selectCascaderVO(pid);
        return new ResultToken(ResultCode.SUCCESS, list, this.newToken);
    }

    /**
     * 菜单列表
     */
    // @RequiresPermissions(value = "/admin/v1/menus/all:list")
    @RequestMapping(value = "/menus/all", method = RequestMethod.GET)
    public ResultToken menuListAll() {
        List<IdNamePidVO> list = resourceService.selectIdNamePidVO();
        return new ResultToken(ResultCode.SUCCESS, list, this.newToken);
    }



}
