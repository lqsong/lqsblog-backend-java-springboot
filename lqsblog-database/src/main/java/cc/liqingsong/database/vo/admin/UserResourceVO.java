package cc.liqingsong.database.vo.admin;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户对应的资源 VO
 * @author liqingsong
 */
@Data
public class UserResourceVO implements Serializable {

    /**
     * 资源 ID
     */
    private Long id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源编号
     */
    private String urlcode;

    /**
     * 类型 1、菜单 2、按钮
     */
    private Long type;

}
