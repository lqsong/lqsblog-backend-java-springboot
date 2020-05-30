package cc.liqingsong.service.pc;

import cc.liqingsong.database.dto.pc.WorksSearchDTO;
import cc.liqingsong.database.entity.Works;
import cc.liqingsong.database.vo.pc.WorksDetailVO;
import cc.liqingsong.database.vo.pc.WorksListVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

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
    IPage<WorksListVO> WorksListVOPage(IPage<Works> page, WorksSearchDTO worksSearchDTO);

    /**
     * 根据搜索信息分页
     *
     * @param page 分页对象
     * @param worksSearchDTO  搜索信息
     * @return
     */
    IPage<Works> page(IPage<Works> page, WorksSearchDTO worksSearchDTO);

    /**
     * 根据id查找详情
     *
     * @param id ID
     * @return
     */
    WorksDetailVO WorksDetailVOById(Serializable id);

    /**
     * 根据id查找详情，并添加浏览量
     *
     * @param id ID
     * @return
     */
    WorksDetailVO WorksDetailVOByIdAndAddHit(Serializable id);

}
