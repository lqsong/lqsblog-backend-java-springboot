package cc.liqingsong.database.vo.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 标签信息 VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class TagVO {

    /** 主键ID */
    private Long id;

    /** 名称 */
    private String name;

}
