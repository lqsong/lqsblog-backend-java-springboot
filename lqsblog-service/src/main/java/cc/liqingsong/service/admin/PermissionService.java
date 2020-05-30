package cc.liqingsong.service.admin;

import cc.liqingsong.database.entity.Permission;
import cc.liqingsong.database.vo.admin.CascaderVO;
import cc.liqingsong.database.vo.admin.IdNamePidVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Permission 服务类
 *
 * @author liqingsong
 */
public interface PermissionService extends IService<Permission> {


    /**
     * 查询指定用户权限列表
     * @param userId 用户 ID
     * @return
     */
    List<Permission> listPermissionByUserId(Long userId);


    /**
     * 根据pId搜索列表
     *
     * @param pid 父id
     * @return
     */
    List<Permission> listByPid(Long pid);

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
