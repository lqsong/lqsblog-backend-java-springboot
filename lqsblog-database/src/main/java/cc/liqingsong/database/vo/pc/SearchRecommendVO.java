package cc.liqingsong.database.vo.pc;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 搜索推荐列表 VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class SearchRecommendVO {

    /** 主键ID */
    @JsonSerialize(using= ToStringSerializer.class) // 解决js 数字精度损失
    private Long sid;

    /** 主键ID */
    private Long id;

    /** 类型 1 文章 2 作品 */
    private Long type;

    /** 标题 */
    private String title;

    /** 缩略图多个 | 分割 */
    private String thumb;

}
