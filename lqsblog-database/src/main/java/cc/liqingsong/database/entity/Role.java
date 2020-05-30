package cc.liqingsong.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Role 实体类
 * @author liqingsong
 */
@Data
@TableName(value = "sys_role",keepGlobalPrefix = true)
public class Role {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 角色名称 */
    private String name;

    /** 角色描述 */
    private String description;

    /** 资源表id , 分割 */
    private String resources;

    /** 资源表层级id,分割：1-2-3,1-2-4 */
    private String resourcesLevel;

}
