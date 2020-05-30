package cc.liqingsong.database.dto.admin;

import cc.liqingsong.database.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用户 DTO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class UserDTO extends User {

    /** 用户角色id 数组*/
    private List<Long> roles;
}
