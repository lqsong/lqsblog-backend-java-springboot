package cc.liqingsong.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * LinkCategory 实体类
 * @author liqingsong
 */
@Data
public class LinkCategory {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 名称 */
    private String name;

    /** 别名 */
    private String alias;

    /** 排序 */
    private Long sort;

}
