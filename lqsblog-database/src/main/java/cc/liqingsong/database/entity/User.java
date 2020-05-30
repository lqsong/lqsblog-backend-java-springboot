package cc.liqingsong.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * User 实体类
 * @author liqingsong
 */
@Data
@TableName(value = "sys_user",keepGlobalPrefix = true)
public class User {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 加盐 */
    private String salt;

    /** 昵称 */
    private String nickname;

    /** 是否锁定 */
    private Boolean locked;

}
