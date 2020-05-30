package cc.liqingsong.database.vo.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class ArticleListVO {
    /** 主键ID */
    private Long id;

    /** 最后一级分类 */
    private Object category;

    /** 分类ids ,分割 */
    private String categoryIds;

    /** 标题 */
    private String title;


    /** 标签, 分割 */
    private String tag;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addtime;

    /** 点击数 */
    private Long hit;
}
