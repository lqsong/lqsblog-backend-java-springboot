package cc.liqingsong.database.dto.pc;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文章搜索 DTO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class ArticleSearchDTO {

    /** 分类id */
    private Long categoryId;

    /** 排序字段[addtime,id,hit] */
    private Integer sort;

    /** [desc 降序，asc 升序] */
    private Integer order;


}
