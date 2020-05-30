package cc.liqingsong.database.vo.pc;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 专题 VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class TopicsDetailVO {
    /** 主键ID */
    private Long id;

    /** 标题 */
    private String title;

    /** keywords */
    private String keywords;

    /** description */
    private String description;

    /** 列表 */
    private List list;

    /** 点击数 */
    private Long hit;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addtime;

}
