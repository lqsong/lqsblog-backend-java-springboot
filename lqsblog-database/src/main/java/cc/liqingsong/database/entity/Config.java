package cc.liqingsong.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * Config 实体类
 * @author liqingsong
 */
@Data
public class Config {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 别名（标识） */
    private String name;

    /** 标题 */
    private String title;

    /** 值 */
    private String content;

}
