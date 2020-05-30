package cc.liqingsong.database.vo.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 父子层级 VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class IdNamePidVO {

    /** 主键ID */
    private Long id;

    /** 名称 */
    private String name;

    /** 父id */
    private Long pid;

}
