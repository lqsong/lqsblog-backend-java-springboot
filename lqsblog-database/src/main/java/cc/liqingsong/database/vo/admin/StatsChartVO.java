package cc.liqingsong.database.vo.admin;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 统计 chart VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class StatsChartVO {

    /** 总数量 */
    private List num;

    /** 每日日期 */
    private List day;

}
