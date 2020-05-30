package cc.liqingsong.database.mapper;

import cc.liqingsong.database.entity.UserRole;
import cc.liqingsong.database.vo.admin.UserRoleUidVO;
import cc.liqingsong.database.vo.admin.UserRoleVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserRole Mapper
 * @author liqingsong
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户 ID 查询用户角色 VO
     * @param userId 用户 ID
     * @return
     */
    List<UserRoleVO> selectUserRoleVO(@Param("userId") Long userId);

    /**
     * 根据用户 ID数组 查询角色列表 VO
     * @param userIds 用户 ID数组
     * @return
     */
    List<UserRoleUidVO> selectUserRoleUidVO(@Param("userIds") List userIds);

}
