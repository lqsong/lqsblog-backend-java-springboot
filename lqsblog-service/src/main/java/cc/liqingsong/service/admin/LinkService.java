package cc.liqingsong.service.admin;

import cc.liqingsong.database.dto.admin.LinkSearchDTO;
import cc.liqingsong.database.entity.Link;
import cc.liqingsong.database.vo.admin.LinkListVO;
import cc.liqingsong.database.vo.admin.LinkVO;
import cc.liqingsong.database.vo.admin.StatsTotalChartVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Link 服务类
 *
 * @author liqingsong
 */
public interface LinkService extends IService<Link> {



    /**
     * 根据搜索信息分页
     *
     * @param page 分页对象
     * @param linkSearchDTO  搜索信息
     * @return
     */
    IPage<LinkListVO> listVOPage(IPage<Link> page, LinkSearchDTO linkSearchDTO);


    /**
     * 根据搜索信息分页
     *
     * @param page 分页对象
     * @param linkSearchDTO  搜索信息
     * @return
     */
    IPage<Link> page(IPage<Link> page, LinkSearchDTO linkSearchDTO);

    /**
     * 根据id 查询 定义格式的详情
     *
     * @param id ID
     * @return
     */
    LinkVO getLinkVOById(Long id);

    /**
     * 统计 - 年新增，总量，chart数据
     *
     * @return
     */
    StatsTotalChartVO getStatsTotalChartVO();


}
