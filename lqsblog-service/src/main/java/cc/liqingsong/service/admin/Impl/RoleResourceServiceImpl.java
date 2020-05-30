package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.RoleResource;
import cc.liqingsong.database.mapper.RoleResourceMapper;
import cc.liqingsong.database.vo.admin.UserResourceVO;
import cc.liqingsong.service.admin.RoleResourceService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * RoleResource 服务实现类
 *
 * @author liqingsong
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceService {

    @Override
    public List<UserResourceVO> listUserResourceVO(List<Object> roleIds) {
        return baseMapper.selectUserResourceVO(roleIds);
    }

    @Override
    public boolean removeByRoleId(Serializable roleId) {

        Assert.fail(null == roleId, ResultCode.ID_REQUIRED);

        QueryWrapper<RoleResource> qw = new QueryWrapper<>();
        qw.eq("role_id",roleId);

        return super.remove(qw);
    }

}
