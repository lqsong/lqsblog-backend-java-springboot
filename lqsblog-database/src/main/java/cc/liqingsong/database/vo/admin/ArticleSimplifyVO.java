package cc.liqingsong.database.vo.admin;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * 文章 VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class ArticleSimplifyVO {

    /** 主键ID */
    private Long id;

    /** 标题 */
    private String title;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addtime;



}
