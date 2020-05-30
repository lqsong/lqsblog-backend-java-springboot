package cc.liqingsong.database.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * SearchHotwordLog 实体类
 * @author liqingsong
 */
@Data
public class SearchHotwordLog {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** ip地址 */
    private String ip;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 搜索词 */
    private String hotword;

    /** 国家 */
    private String country;

    /** 地区(省) */
    private String region;

    /** 市 */
    private String city;

    /** 区 */
    private String area;

    /** 宽带供应商(电信、联通、移动) */
    private String isp;

}
