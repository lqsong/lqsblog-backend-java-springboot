package cc.liqingsong.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Resource 实体类
 * @author liqingsong
 */
@Data
@TableName(value = "sys_resource",keepGlobalPrefix = true)
public class Resource {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 资源名称 */
    private String name;

    /** 资源编号 */
    private String urlcode;

    /** 类型 1、菜单 2、按钮 */
    private Long type;

    /** 权限id , 分割 */
    private String perms;

    /** 权限层级id,分割：1-2-3,1-2-4 */
    private String permsLevel;

    /** 父节点 */
    private Long pid;


}
