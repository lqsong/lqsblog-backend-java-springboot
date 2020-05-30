package cc.liqingsong.service.admin;

import cc.liqingsong.database.entity.ResourcePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * ResourcePermission 服务类
 *
 * @author liqingsong
 */
public interface ResourcePermissionService  extends IService<ResourcePermission> {

    /**
     * 根据 resource_id 删除数据
     *
     * @param resourceId  resource_id
     */
    boolean removeByResourceId(Serializable resourceId);


}
