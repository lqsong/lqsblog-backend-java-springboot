package cc.liqingsong.database.dto.pc;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;

/**
 * 搜索表搜索 DTO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class SearchSearchDTO {

    /** 关键词 */
    private String keywords;

    /** 不包含的搜索id */
    private ArrayList noSid;

    /** 分类id */
    private Long categoryId;

    /** 标签 */
    private String tag;

    /** 排序字段[addtime,sid] */
    private Integer sort;

    /** [desc 降序，asc 升序] */
    private Integer order;


}
