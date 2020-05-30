package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.Permission;
import cc.liqingsong.database.enums.PermissionCode;
import cc.liqingsong.database.mapper.PermissionMapper;
import cc.liqingsong.database.vo.admin.CascaderVO;
import cc.liqingsong.database.vo.admin.IdNamePidVO;
import cc.liqingsong.service.admin.PermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Permission 服务实现类
 *
 * @author liqingsong
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public List<Permission> listPermissionByUserId(Long userId) {
        return baseMapper.selectPermissionByUserId(userId);
    }

    @Override
    public List<Permission> listByPid(Long pid) {
        if (null == pid) {
            return new ArrayList<>();
        }
        QueryWrapper<Permission> qw = new QueryWrapper<>();
        qw.eq("pid",pid);
        return super.list(qw);
    }

    @Override
    public List<CascaderVO> selectCascaderVO(Long pid) {
        if (null == pid) {
            return new ArrayList<>();
        }
        return baseMapper.selectCascaderVO(pid);
    }

    @Override
    public List<IdNamePidVO> selectIdNamePidVO() {
        return baseMapper.selectIdNamePidVO();
    }

    /**
     * 插入一条记录
     *
     * @param entity 实体类对象
     */
    @Override
    public boolean save(Permission entity) {
        if (null == entity) {
            return false;
        }

        Assert.fail(null == entity.getPid(), ResultCode.PID_REQUIRED);

        saveUpdateVerify(entity);

        if(entity.getPid() > 0) {
            Permission permission = super.getById(entity.getPid());
            Assert.fail( null == permission, ResultCode.PID_NOT_FOUND);
        }

        return super.save(entity);
    }

    /**
     * 删除一条记录根据id
     *
     * @param id ID
     */
    @Override
    public boolean removeById(Serializable id) {

        QueryWrapper<Permission> qw = new QueryWrapper<>();
        qw.eq("pid",id);
        int count = super.count(qw);
        Assert.fail( count > 0, ResultCode.UNABLE_TO_DEL_HAVING_CHILD);

        return super.removeById(id);
    }

    /**
     * 根据 ID 修改
     *
     * @param entity 实体类对象
     */
    @Override
    public boolean updateById(Permission entity) {

        if (null == entity) {
            return false;
        }

        Assert.fail(null == entity.getId(), ResultCode.ID_NOT_FOUND);

        // 针对父id不做修改
        entity.setPid(null);

        saveUpdateVerify(entity);


        return super.updateById(entity);
    }


    /**
     * 私用验证
     *
     * @param entity 实体类对象
     */
    private void saveUpdateVerify(Permission entity) {

        Assert.fail(null == entity.getName(), ResultCode.NAME_NOT_EMPTY);
        int nameLen = entity.getName().length();
        Assert.fail(nameLen > 8 || nameLen < 1, PermissionCode.TITLE_LENGTH_WORDS);

        Assert.fail(null == entity.getPermission(), ResultCode.INCORRECT_PARAMETER);
        int perLen = entity.getPermission().length();
        Assert.fail(perLen > 100, PermissionCode.PERMISSION_LENGTH_WORDS);

    }






}
