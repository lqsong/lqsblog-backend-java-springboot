package cc.liqingsong.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * UserRole 实体类
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
@TableName(value = "sys_user_role",keepGlobalPrefix = true)
public class UserRole {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long roleId;

}
