package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.api.admin.utils.JwtImgCodeToken;
import cc.liqingsong.api.admin.utils.JwtUserToken;
import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.entity.Result;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.admin.LoginDTO;
import cc.liqingsong.database.entity.User;
import cc.liqingsong.database.enums.UserCode;
import cc.liqingsong.database.vo.admin.LoginVO;
import cc.liqingsong.service.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 登录控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1")
public class LoginController {

    private JwtImgCodeToken jwtImgCodeToken;
    @Autowired
    public void setJwtImgCodeToken(JwtImgCodeToken jwtImgCodeToken) {
        this.jwtImgCodeToken = jwtImgCodeToken;
    }

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private JwtUserToken jwtUserToken;
    @Autowired
    public void setJwtUserToken(JwtUserToken jwtUserToken) {
        this.jwtUserToken = jwtUserToken;
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public Result index(@RequestBody LoginDTO loginDTO) {

        Assert.fail(null == loginDTO.getImgCode(), UserCode.LOGIN_IMGCODE_NOT_EMPTY);
        Assert.fail(null == loginDTO.getImgCodeToken(), UserCode.LOGIN_IMGCODE_NOT_EMPTY);

        String code = jwtImgCodeToken.parseImgCode(loginDTO.getImgCodeToken());
        Assert.fail(!loginDTO.getImgCode().equals(code) , UserCode.LOGIN_IMGCODE_ERROR);

        // 纯jwt 登录认证
        /*
        User user = userService.loginUser(loginDTO);

        String token = jwtUserToken.getToken(user);

        return new Result(ResultCode.SUCCESS, token);
        */

        // 存 shiro 登录认证
        /*
        String sessionId = null;
        try {
            // 1、构造登录令牌
            UsernamePasswordToken upToken = new UsernamePasswordToken(username,password);
            // 2、获取subject
            Subject subject = SecurityUtils.getSubject();
            // 3、调用login,进入realm认证
            subject.login(upToken);
            // 4、获取sessionid
            sessionId = (String) subject.getSession().getId();
            // 5、构造返回结果
        } catch (Exception e) {
            System.out.println(e);
            Assert.fail("用户或密码错误！");
        }

        return new Result(ResultCode.SUCCESS, sessionId);
        */

        // shiro + jwt
        User user = userService.loginUser(loginDTO);

        String token = jwtUserToken.getToken(user);

        return new Result(ResultCode.SUCCESS, new LoginVO().setToken(token));

    }

}
