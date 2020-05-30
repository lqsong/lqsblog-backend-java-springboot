package cc.liqingsong.database.dto.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户搜索 DTO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class UserSearchDTO {

    /** 关键词 */
    private String keywords;

    /** 排序字段[id] */
    private Integer sort;

    /** [desc 降序，asc 升序] */
    private Integer order;

}
