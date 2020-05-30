package cc.liqingsong.database.vo.admin;

import lombok.Data;

import java.util.List;

/**
 * 用户 VO
 * @author liqingsong
 */
@Data
public class UserVO {

    /** 主键ID */
    private Long id;

    /** 用户名 */
    private String username;

    /** 昵称 */
    private String nickname;

    /** 是否锁定 */
    private Boolean locked;

    /** 角色 */
    private List<UserRoleVO> roles;


}
