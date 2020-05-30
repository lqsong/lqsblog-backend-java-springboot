package cc.liqingsong.database.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Topics 实体类
 * @author liqingsong
 */
@Data
public class Topics {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 标题 */
    private String title;

    /** 别名 */
    private String alias;

    /** keywords */
    private String keywords;

    /** description */
    private String description;

    /** 内容 */
    @TableField(exist = false)
    private Object content;
    @TableField(value = "content")
    private String contentStr;

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
