package cc.liqingsong.service.pc;

import cc.liqingsong.database.dto.pc.TopicsSearchDTO;
import cc.liqingsong.database.entity.Topics;
import cc.liqingsong.database.vo.pc.TopicsDetailVO;
import cc.liqingsong.database.vo.pc.TopicsListVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Topics 服务类
 *
 * @author liqingsong
 */
public interface TopicsService extends IService<Topics> {

    /**
     * 根据标签信息分页
     *
     * @param page 分页对象
     * @param topicsSearchDTO  搜索信息
     * @return
     */
    IPage<TopicsListVO> TopicsListVOPage(IPage<Topics> page, TopicsSearchDTO topicsSearchDTO);


    /**
     * 根据标签信息分页
     *
     * @param page 分页对象
     * @param topicsSearchDTO  搜索信息
     * @return
     */
    IPage<Topics> page(IPage<Topics> page, TopicsSearchDTO topicsSearchDTO);

    /**
     * 根据别名查询信息
     *
     * @param alias 别名
     * @return
     */
    TopicsDetailVO TopicsDetailVOByAlias(String alias);


    /**
     * 根据别名查询信息，并添加浏览量
     *
     * @param alias 别名
     * @return
     */
    TopicsDetailVO TopicsDetailVOByAliasAndAddHit(String alias);

}
