package cc.liqingsong.service.pc;

import cc.liqingsong.database.entity.Link;
import cc.liqingsong.database.vo.pc.LinkCategoryVO;
import cc.liqingsong.database.vo.pc.LinkVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Link 服务类
 *
 * @author liqingsong
 */
public interface LinkService  extends IService<Link> {

    /**
     * 获取所有分类链接列表
     *
     * @return
     */
    List<LinkCategoryVO> selectLinkCategoryVOAll();

    /**
     * 根据分类id查询列表
     *
     * @param id  分类id
     * @return
     */
    List<LinkVO> getByCategoryId(List id);
}
