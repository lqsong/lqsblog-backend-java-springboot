package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.common.entity.Result;
import cc.liqingsong.common.entity.ResultToken;
import cc.liqingsong.api.admin.controller.BaseController;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.User;
import cc.liqingsong.database.vo.admin.LoginUserVO;

import cc.liqingsong.service.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1")
public class UserController extends BaseController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    /**
     * 获取当前登录用户信息
     */
    // @RequiresPermissions(value = "/admin/v1/user/info:query") // 不设置默认所有用户都有权限
    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    public ResultToken info() {
        User user = new User();
        user.setId(this.userId);
        user.setUsername(this.username);
        user.setNickname(this.nickname);
        LoginUserVO loginUserVO = userService.setLoginUserVO(user);
        return new ResultToken(ResultCode.SUCCESS, loginUserVO,this.newToken);
    }

    /**
     * 退出
     */
    // @RequiresPermissions(value = "/admin/v1/user/logout:post") // 不设置默认所有用户都有权限
    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public Result logout() {
        /**
         * 1、这里后端不做操作，前端直接清空token
         * 2、如果做操作，可以结合数据库做白名单或黑名单
         */
        return new Result(ResultCode.SUCCESS);
    }

}
