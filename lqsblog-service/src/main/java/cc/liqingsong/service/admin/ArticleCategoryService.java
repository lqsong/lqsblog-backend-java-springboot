package cc.liqingsong.service.admin;

import cc.liqingsong.database.entity.ArticleCategory;
import cc.liqingsong.database.vo.admin.CascaderVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * ArticleCategory 服务类
 *
 * @author liqingsong
 */
public interface ArticleCategoryService extends IService<ArticleCategory> {

    /**
     * 根据pId搜索列表
     *
     * @param pId 父id
     * @return
     */
    List<ArticleCategory> listByPid(Long pId);


    /**
     * 根据pid搜索 返回联动列表
     *
     * @param pId 父id
     * @return
     */
    List<CascaderVO> selectCascaderVO(Long pId);



}
