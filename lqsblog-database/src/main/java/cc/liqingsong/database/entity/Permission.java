package cc.liqingsong.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Permission 实体类
 * @author liqingsong
 */
@Data
@TableName(value = "sys_permission",keepGlobalPrefix = true)
public class Permission {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 权限名称 */
    private String name;

    /** 权限编号 */
    private String permission;

    /** 权限描述 */
    private String description;

    /** 父节点 */
    private Long pid;

}
