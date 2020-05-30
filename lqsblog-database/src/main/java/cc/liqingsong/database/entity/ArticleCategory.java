package cc.liqingsong.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * ArticleCategory 实体类
 * @author liqingsong
 */
@Data
public class ArticleCategory {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 父id */
    private Long pId;

    /** 名称 */
    private String name;

    /** 别名 */
    private String alias;

    /** title */
    private String title;

    /** keywords */
    private String keywords;

    /** description */
    private String description;

    /** 点击数 */
    private Long hit;


}
