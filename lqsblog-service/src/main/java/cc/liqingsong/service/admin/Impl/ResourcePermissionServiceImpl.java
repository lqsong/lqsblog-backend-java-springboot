package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.ResourcePermission;
import cc.liqingsong.database.mapper.ResourcePermissionMapper;
import cc.liqingsong.service.admin.ResourcePermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * ResourcePermission 服务实现类
 *
 * @author liqingsong
 */
@Service
public class ResourcePermissionServiceImpl  extends ServiceImpl<ResourcePermissionMapper, ResourcePermission> implements ResourcePermissionService {


    @Override
    public boolean removeByResourceId(Serializable resourceId) {

        Assert.fail(null == resourceId, ResultCode.ID_REQUIRED);

        QueryWrapper<ResourcePermission> qw = new QueryWrapper<>();
        qw.eq("resource_id",resourceId);

        return super.remove(qw);
    }
}
