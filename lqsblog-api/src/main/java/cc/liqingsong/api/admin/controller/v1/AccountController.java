package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.api.admin.controller.BaseController;
import cc.liqingsong.common.entity.PageData;
import cc.liqingsong.common.entity.ResultToken;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.admin.UserDTO;
import cc.liqingsong.database.dto.admin.UserSearchDTO;
import cc.liqingsong.database.vo.admin.SaveIdVO;
import cc.liqingsong.database.vo.admin.UserVO;
import cc.liqingsong.service.admin.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 账号控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1")
public class AccountController extends BaseController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    /**
     * 账号列表
     */
    @RequiresPermissions(value = "/admin/v1/accounts:list")
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public ResultToken accountList(UserSearchDTO userSearchDTO) {
        IPage page = userService.userVOPage(getPage(), userSearchDTO);
        return new ResultToken(ResultCode.SUCCESS, new PageData<>(page), this.newToken);
    }

    /**
     * 账号添加
     */
    @RequiresPermissions(value = "/admin/v1/accounts:create")
    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResultToken accountCreate(@RequestBody UserDTO userDTO) {
        boolean save = userService.save(userDTO);
        return save ? new ResultToken(ResultCode.SUCCESS, new SaveIdVO().setId(userDTO.getId()),this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 账号编辑
     */
    @RequiresPermissions(value = "/admin/v1/accounts:update")
    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.PUT)
    public ResultToken accountUpdate(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        boolean b = userService.updateById(userDTO);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 账号删除
     */
    @RequiresPermissions(value = "/admin/v1/accounts:delete")
    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.DELETE)
    public ResultToken accountDelete(@PathVariable Long id) {
        boolean b = userService.removeById(id);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 账号详情
     */
    @RequiresPermissions(value = "/admin/v1/accounts:read")
    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
    public ResultToken accountRead(@PathVariable Long id) {
        UserVO vo = userService.getUserVOById(id);
        return new ResultToken(ResultCode.SUCCESS, vo,this.newToken);
    }




}
