package cc.liqingsong.database.vo.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 联动下拉 VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class CascaderVO {

    /** 主键ID */
    private Long id;

    /** 名称 */
    private String name;

    /** 是否为最后一级 */
    private Boolean leaf;

}
