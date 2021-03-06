package cc.liqingsong.database.vo.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 左邻右舍 VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class LinkListVO {
    /** 主键ID */
    private Long id;

    /** 最后一级分类 */
    private Object category;

    /** 标题 */
    private String title;

    /** 点击数 */
    private Long hit;
}
