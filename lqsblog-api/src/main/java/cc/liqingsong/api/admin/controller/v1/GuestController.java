package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.api.admin.utils.JwtImgCodeToken;
import cc.liqingsong.common.entity.Result;
import cc.liqingsong.common.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1/guest")
public class GuestController {

    private JwtImgCodeToken jwtImgCodeToken;
    @Autowired
    public void setJwtImgCodeToken(JwtImgCodeToken jwtImgCodeToken) {
        this.jwtImgCodeToken = jwtImgCodeToken;
    }

    /**
     * 图片验证码
     */
    @RequestMapping(value = "/validateCodeImg", method = RequestMethod.GET)
    public Result Img() {
        return new Result(ResultCode.SUCCESS, jwtImgCodeToken.createImgCode());
    }

}
