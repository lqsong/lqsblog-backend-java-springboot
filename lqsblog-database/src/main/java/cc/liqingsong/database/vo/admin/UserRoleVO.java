package cc.liqingsong.database.vo.admin;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户的角色 VO
 * @author liqingsong
 */
@Data
public class UserRoleVO implements Serializable {

    /**
     * 角色 ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

}
