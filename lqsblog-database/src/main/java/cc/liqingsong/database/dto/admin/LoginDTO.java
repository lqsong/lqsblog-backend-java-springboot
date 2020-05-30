package cc.liqingsong.database.dto.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登录 DTO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class LoginDTO {

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 图片验证码值 */
    private String imgCode;

    /** 图片验证码值对应的token */
    private String imgCodeToken;
}
