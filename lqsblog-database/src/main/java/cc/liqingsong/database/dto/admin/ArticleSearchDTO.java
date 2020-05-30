package cc.liqingsong.database.dto.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文章搜索 DTO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class ArticleSearchDTO {

    /** 关键词 */
    private String keywords;

    /** 分类id */
    private Long categoryid;

    /** 发布时间开始 */
    private String addtimestart;

    /** 发布时间截止 */
    private String addtimeend;

    /** 标签 */
    private String tags;

    /** 排序字段[id,hit,addtime] */
    private Integer sort;

    /** [desc 降序，asc 升序] */
    private Integer order;


}
