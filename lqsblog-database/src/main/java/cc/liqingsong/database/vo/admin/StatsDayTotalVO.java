package cc.liqingsong.database.vo.admin;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 统计 - 日期总数
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class StatsDayTotalVO {

    /** 总数量 */
    private int num;

    /** 日期 */
    private String day;

}
