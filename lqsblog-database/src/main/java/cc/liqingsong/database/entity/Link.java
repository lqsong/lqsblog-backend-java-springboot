package cc.liqingsong.database.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Link 实体类
 * @author liqingsong
 */
@Data
public class Link {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 最后一级分类id */
    private Long categoryId;

    /** 标题 */
    private String title;

    /** description */
    private String description;

    /** logo */
    private String logo;

    /** 超链接 */
    private String href;

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
