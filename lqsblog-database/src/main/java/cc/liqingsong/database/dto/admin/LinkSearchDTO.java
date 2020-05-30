package cc.liqingsong.database.dto.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 左邻右舍搜索 DTO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class LinkSearchDTO {

    /** 关键词 */
    private String keywords;

    /** 分类id */
    private Long categoryid;

    /** 排序字段[id,hit] */
    private Integer sort;

    /** [desc 降序，asc 升序] */
    private Integer order;


}
