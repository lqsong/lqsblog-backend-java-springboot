package cc.liqingsong.database.vo.pc;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 专题 VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class TopicsListVO {
    /** 主键ID */
    private Long id;

    /** 标题 */
    private String title;

    /** 别名 */
    private String alias;

    /** 内容数量 */
    private Long quantity;

    /** 内容 最多显示3个 {id，title，type，thumb} */
    private List conlist;

}
