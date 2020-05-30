package cc.liqingsong.database.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Search 实体类
 * @author liqingsong
 */
@Data
public class Search {

    /** 主键ID */
    @TableId
    private Long sid;

    /** 文章或作品id */
    private Long id;

    /** 类型 1 文章 2 作品 */
    private Long type;

    /** 最后一级分类id */
    private Long categoryId;

    /** 标题 */
    private String title;

    /** keywords */
    private String keywords;

    /** description */
    private String description;

    /** 缩略图多个 | 分割 */
    private String thumb;

    /** 标签, 分割 */
    private String tag;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addtime;

    /** 搜索关键词 */
    private String keyPrecise;


}
