package cc.liqingsong.database.vo.admin;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 登录返回信息 VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class LoginVO {

    /** token */
    private String token;
}
