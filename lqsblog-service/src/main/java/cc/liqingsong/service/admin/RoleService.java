package cc.liqingsong.service.admin;

import cc.liqingsong.database.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Role 服务类
 *
 * @author liqingsong
 */
public interface RoleService extends IService<Role> {


    /**
     * 批量插入角色资源关联表记录
     *
     * @param entity 实体类对象
     */
    boolean saveBatchRoleResource(Role entity);


}
