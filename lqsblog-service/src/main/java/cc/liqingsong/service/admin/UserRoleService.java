package cc.liqingsong.service.admin;

import cc.liqingsong.database.entity.UserRole;
import cc.liqingsong.database.vo.admin.UserRoleUidVO;
import cc.liqingsong.database.vo.admin.UserRoleVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * UserRole 服务类
 *
 * @author liqingsong
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 查询用户对应角色 VO
     *
     * @param userId 用户 ID
     * @return
     */
    List<UserRoleVO> listVO(Long userId);


    /**
     * 根据 user_id 删除数据
     *
     * @param userId  user_id
     */
    boolean removeByUserId(Serializable userId);


    /**
     * 根据用户ID条件查询角色列表 VO
     *
     * @param userIds 用户 ID 数组
     * @return
     */
    List<UserRoleUidVO> listUserRoleUidVO(List userIds);

}
