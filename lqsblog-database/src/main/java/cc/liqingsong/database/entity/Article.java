package cc.liqingsong.database.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Article 实体类
 * @author liqingsong
 */
@Data
public class Article implements Serializable {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 最后一级分类id */
    private Long categoryId;

    /** 分类ids ,分割 */
    private String categoryIds;

    /** 标题 */
    private String title;

    /** keywords */
    private String keywords;

    /** description */
    private String description;

    /** 缩略图多个 | 分割 */
    private String thumb;

    /** 内容 */
    private String content;

    /** 标签, 分割 */
    private String tag;

    /** 推荐随笔Ids , 分割 */
    private String interestIds;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addtime;

    /** 点击数 */
    private Long hit;

    /** 创建人id */
    @TableField(fill = FieldFill.INSERT)
    private Long creatorId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
