package cc.liqingsong.service.admin;

import cc.liqingsong.database.dto.admin.WorksSearchDTO;
import cc.liqingsong.database.entity.Works;
import cc.liqingsong.database.vo.admin.StatsTotalChartVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Works 服务类
 *
 * @author liqingsong
 */
public interface WorksService extends IService<Works> {

    /**
     * 根据搜索信息分页
     *
     * @param page 分页对象
     * @param worksSearchDTO  搜索信息
     * @return
     */
    IPage<Works> page(IPage<Works> page, WorksSearchDTO worksSearchDTO);

    /**
     * 统计 - 周新增，总量，chart数据
     *
     * @return
     */
    StatsTotalChartVO getStatsTotalChartVO();

}
