package cc.liqingsong.service.admin;

import cc.liqingsong.database.dto.admin.TopicsSearchDTO;
import cc.liqingsong.database.entity.Topics;
import cc.liqingsong.database.vo.admin.StatsTotalChartVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Topics 服务类
 *
 * @author liqingsong
 */
public interface TopicsService  extends IService<Topics> {

    /**
     * 根据标签信息分页
     *
     * @param page 分页对象
     * @param topicsSearchDTO  搜索信息
     * @return
     */
    IPage<Topics> page(IPage<Topics> page, TopicsSearchDTO topicsSearchDTO);


    /**
     * 统计 - 月新增，总量，chart数据
     *
     * @return
     */
    StatsTotalChartVO getStatsTotalChartVO();

}
