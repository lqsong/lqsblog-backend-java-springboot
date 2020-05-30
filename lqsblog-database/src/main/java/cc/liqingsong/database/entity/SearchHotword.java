package cc.liqingsong.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * SearchHotword 实体类
 * @author liqingsong
 */
@Data
public class SearchHotword {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 搜索词 */
    private String name;

    /** 搜索次数 */
    private Long hit;

}
