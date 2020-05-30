package cc.liqingsong.database.vo.admin;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 当前登录用户信息 VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class LoginUserVO {

    /** 昵称 nickname */
    private String name;

    /** 头像 */
    private String avatar;

    /** 角色集合 */
    private List<String> roles;

    /** 资源集合 */
    private List<String> resources;

    /** 消息 */
    private Long msgtotal;

}
