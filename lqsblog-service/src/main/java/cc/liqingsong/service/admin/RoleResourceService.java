package cc.liqingsong.service.admin;

import cc.liqingsong.database.entity.RoleResource;
import cc.liqingsong.database.vo.admin.UserResourceVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * RoleResource 服务类
 *
 * @author liqingsong
 */
public interface RoleResourceService extends IService<RoleResource> {


    /**
     * 根据用户角色 ID 查询用户对应的资源
     *
     * @param  roleIds 角色id
     * @return
     */
    List<UserResourceVO> listUserResourceVO(List<Object> roleIds);


    /**
     * 根据 role_id 删除数据
     *
     * @param roleId  role_id
     */
    boolean removeByRoleId(Serializable roleId);


}
