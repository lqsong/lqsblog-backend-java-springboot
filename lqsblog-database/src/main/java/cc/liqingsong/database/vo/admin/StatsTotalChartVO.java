package cc.liqingsong.database.vo.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 统计 - 周/月/年新增，总量，chart数据
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class StatsTotalChartVO {


    /** 总数字量 */
    private int total;

    /** 新增数量 */
    private int num;

    /** chart 数据 { day:["2020-01-01"], num:[10] } */
    private StatsChartVO chart;



}
