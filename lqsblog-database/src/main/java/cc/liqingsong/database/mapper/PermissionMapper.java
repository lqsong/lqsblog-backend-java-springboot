package cc.liqingsong.database.mapper;

import cc.liqingsong.database.entity.Permission;
import cc.liqingsong.database.vo.admin.CascaderVO;
import cc.liqingsong.database.vo.admin.IdNamePidVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  Permission Mapper
 * @author liqingsong
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 查询用户Api权限
     * @param userId 用户 ID
     * @return
     */
    List<Permission> selectPermissionByUserId(@Param("userId") Long userId);

    /**
     * 根据pid搜索 返回联动列表
     *
     * @param pid 父id
     * @return
     */
    List<CascaderVO> selectCascaderVO(@Param("pid") Long pid);

    /**
     * 返回所有列表
     *
     * @return
     */
    List<IdNamePidVO> selectIdNamePidVO();

}
