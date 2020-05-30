package cc.liqingsong.service.admin;

import cc.liqingsong.database.entity.Resource;
import cc.liqingsong.database.vo.admin.CascaderVO;
import cc.liqingsong.database.vo.admin.IdNamePidVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Resource 服务类
 *
 * @author liqingsong
 */
public interface ResourceService extends IService<Resource> {


    /**
     * 批量插入资源权限关联表记录
     *
     * @param entity 实体类对象
     */
    boolean saveBatchResourcePermission(Resource entity);

    /**
     * 根据pId搜索列表
     *
     * @param pid 父id
     * @return
     */
    List<Resource> listByPid(Long pid);

    /**
     * 根据pid搜索 返回联动列表
     *
     * @param pid 父id
     * @return
     */
    List<CascaderVO> selectCascaderVO(Long pid);

    /**
     * 返回全部列表
     *
     * @return
     */
    List<IdNamePidVO> selectIdNamePidVO();
}
