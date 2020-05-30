package cc.liqingsong.database.vo.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 图片验证码Token VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class ImgCodeTokenVO {

    /** 验证码对应 base64图片 */
    private String base64;

    /** 验证码Jwt token 加密字符串 */
    private String tokenCode;

}
