package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.Resource;
import cc.liqingsong.database.entity.ResourcePermission;
import cc.liqingsong.database.enums.ResourceCode;
import cc.liqingsong.database.mapper.ResourceMapper;
import cc.liqingsong.database.vo.admin.CascaderVO;
import cc.liqingsong.database.vo.admin.IdNamePidVO;
import cc.liqingsong.service.admin.ResourcePermissionService;
import cc.liqingsong.service.admin.ResourceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Resource 服务实现类
 *
 * @author liqingsong
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    private ResourcePermissionService resourcePermissionService;
    @Autowired
    public void setResourcePermissionService(ResourcePermissionService resourcePermissionService) {
        this.resourcePermissionService = resourcePermissionService;
    }


    /**
     * 插入一条记录
     *
     * @param entity 实体类对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(Resource entity) {

        if (null == entity) {
            return false;
        }

        Assert.fail(null == entity.getPid(), ResultCode.PID_REQUIRED);

        saveUpdateVerify(entity);

        if(entity.getPid() > 0) {
            Resource resource = super.getById(entity.getPid());
            Assert.fail( null == resource, ResultCode.PID_NOT_FOUND);
        }

        // 必须先执行
        boolean save = super.save(entity);

        // 需要 entity.getId();
        boolean saveBatch = saveBatchResourcePermission(entity);

        return save && saveBatch;
    }

    /**
     * 删除一条记录根据id
     *
     * @param id ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {

        QueryWrapper<Resource> qw = new QueryWrapper<>();
        qw.eq("pid",id);
        int count = super.count(qw);
        Assert.fail( count > 0, ResultCode.UNABLE_TO_DEL_HAVING_CHILD);

        // 清空同资源ID下关联数据
        boolean remove = resourcePermissionService.removeByResourceId(id);

        return super.removeById(id);
    }

    /**
     * 根据 ID 修改
     *
     * @param entity 实体类对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(Resource entity) {

        if (null == entity) {
            return false;
        }

        Assert.fail(null == entity.getId(), ResultCode.ID_NOT_FOUND);

        // 针对父id不做修改
        entity.setPid(null);

        saveUpdateVerify(entity);

        return super.updateById(entity) && saveBatchResourcePermission(entity);
    }


    /**
     * 私用验证
     *
     * @param entity 实体类对象
     */
    private void saveUpdateVerify(Resource entity) {

        Assert.fail(null == entity.getName(), ResultCode.NAME_NOT_EMPTY);
        int nameLen = entity.getName().length();
        Assert.fail(nameLen > 8 || nameLen < 1, ResourceCode.TITLE_LENGTH_WORDS);

        Assert.fail(null == entity.getUrlcode(), ResultCode.INCORRECT_PARAMETER);
        int perLen = entity.getUrlcode().length();
        Assert.fail(perLen > 100, ResourceCode.PERMISSION_LENGTH_WORDS);


        Assert.fail(null == entity.getPerms(), ResultCode.INCORRECT_PARAMETER);
        Assert.fail(null == entity.getPermsLevel(), ResultCode.INCORRECT_PARAMETER);
        Assert.fail(null == entity.getType(), ResultCode.INCORRECT_PARAMETER);

    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatchResourcePermission(Resource entity) {


        Assert.fail(null == entity, ResultCode.ID_REQUIRED);
        Assert.fail(null == entity.getId(), ResultCode.ID_REQUIRED);

        // 先清空同资源ID下数据
        boolean remove = resourcePermissionService.removeByResourceId(entity.getId());


        boolean saveBatch = true;
        String perms = entity.getPerms();
        if(null != perms && perms != "") {
            String[] permsArr = perms.split(",");

            // 再批量添加
            List<ResourcePermission> rplist = new ArrayList<>();
            for (String item : permsArr) {
                ResourcePermission resourcePermission = new ResourcePermission();
                resourcePermission.setResourceId(entity.getId());
                resourcePermission.setPermissionId(Long.valueOf(item));
                rplist.add(resourcePermission);
            }
            saveBatch = resourcePermissionService.saveBatch(rplist);
        }


        return saveBatch;
    }

    @Override
    public List<Resource> listByPid(Long pid) {
        if (null == pid) {
            return new ArrayList<>();
        }
        QueryWrapper<Resource> qw = new QueryWrapper<>();
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
}
