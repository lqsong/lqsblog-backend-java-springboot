package cc.liqingsong.database.vo.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 搜索列表 VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class SearchListVO {
    /** 主键ID */
    private Long id;

    /** 类型 1 文章 2 作品 */
    private Long type;

    /** 最后一级分类 */
    private Object category;

    /** 标题 */
    private String title;

    /** description */
    private String description;

    /** 缩略图多个 | 分割 */
    private String thumb;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addtime;

}
