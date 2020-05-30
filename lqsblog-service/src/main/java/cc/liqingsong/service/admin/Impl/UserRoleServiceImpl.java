package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.UserRole;
import cc.liqingsong.database.mapper.UserRoleMapper;
import cc.liqingsong.database.vo.admin.UserRoleUidVO;
import cc.liqingsong.database.vo.admin.UserRoleVO;
import cc.liqingsong.service.admin.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * UserRole 服务实现类
 *
 * @author liqingsong
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public List<UserRoleVO> listVO(Long userId) {
        return baseMapper.selectUserRoleVO(userId);
    }

    @Override
    public boolean removeByUserId(Serializable userId) {

        Assert.fail(null == userId, ResultCode.ID_REQUIRED);

        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq("user_id",userId);

        return super.remove(qw);
    }

    @Override
    public List<UserRoleUidVO> listUserRoleUidVO(List userIds) {
        return baseMapper.selectUserRoleUidVO(userIds);
    }

}
