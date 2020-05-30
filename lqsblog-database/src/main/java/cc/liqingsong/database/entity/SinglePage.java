package cc.liqingsong.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * SinglePage 实体类
 * @author liqingsong
 */
@Data
public class SinglePage {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 栏目名称 */
    private String name;

    /** 栏目别名 */
    private String alias;

    /** title */
    private String title;

    /** keywords */
    private String keywords;

    /** description */
    private String description;

    /** 值 */
    private String content;

    /** 点击数 */
    private Long hit;


}
