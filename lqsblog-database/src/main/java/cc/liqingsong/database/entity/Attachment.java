package cc.liqingsong.database.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Attachment 实体类
 * @author liqingsong
 */
@Data
public class Attachment {
    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 原文件名 */
    private String fileOldName;

    /** 当前文件名 */
    private String fileName;

    /** 文件子目录 */
    private String fileSubDir;

    /** 文件类型  */
    private String fileType;

    /** 文件大小 */
    private Long fileSize;

    /** 文件后缀 */
    private String fileSuffix;

    /** 创建人id */
    @TableField(fill = FieldFill.INSERT)
    private Long creatorId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
