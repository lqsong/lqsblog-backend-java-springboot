package cc.liqingsong.database.vo.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文章 日新增，总量，日同比，周同比 VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class ArticleDailyNewVO {

    /** 总数字量 */
    private int total;

    /** 今日新增数量 */
    private int num;

    /** 日同比% */
    private double day;

    /** 周同比% */
    private double week;

}
