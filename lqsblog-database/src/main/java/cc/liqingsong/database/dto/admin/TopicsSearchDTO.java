package cc.liqingsong.database.dto.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 专题搜索 DTO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class TopicsSearchDTO {

    /** 关键词 */
    private String keywords;

    /** 排序字段[id,hit] */
    private Integer sort;

    /** [desc 降序，asc 升序] */
    private Integer order;

}
