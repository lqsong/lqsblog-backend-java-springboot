package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.Role;
import cc.liqingsong.database.entity.RoleResource;
import cc.liqingsong.database.enums.ResourceCode;
import cc.liqingsong.database.mapper.RoleMapper;
import cc.liqingsong.service.admin.RoleResourceService;
import cc.liqingsong.service.admin.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Role 服务实现类
 *
 * @author liqingsong
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    private RoleResourceService roleResourceService;
    @Autowired
    public void setRoleResourceService(RoleResourceService roleResourceService) {
        this.roleResourceService = roleResourceService;
    }


    /**
     * 插入一条记录
     *
     * @param entity 实体类对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(Role entity) {

        if (null == entity) {
            return false;
        }

        saveUpdateVerify(entity);

        // 必须先执行
        boolean save = super.save(entity);

        // 需要 entity.getId();
        boolean saveBatch = saveBatchRoleResource(entity);

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

        // 清空同角色ID下关联数据
        boolean b = roleResourceService.removeByRoleId(id);

        return super.removeById(id);
    }


    /**
     * 根据 ID 修改
     *
     * @param entity 实体类对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(Role entity) {

        if (null == entity) {
            return false;
        }

        Assert.fail(null == entity.getId(), ResultCode.ID_NOT_FOUND);

        saveUpdateVerify(entity);

        return super.updateById(entity) && saveBatchRoleResource(entity);
    }


    /**
     * 私用验证
     *
     * @param entity 实体类对象
     */
    private void saveUpdateVerify(Role entity) {

        Assert.fail(null == entity.getName(), ResultCode.NAME_NOT_EMPTY);
        int nameLen = entity.getName().length();
        Assert.fail(nameLen > 8 || nameLen < 1, ResourceCode.TITLE_LENGTH_WORDS);

        Assert.fail(null == entity.getDescription(), ResultCode.INCORRECT_PARAMETER);
        Assert.fail(null == entity.getResources(), ResultCode.INCORRECT_PARAMETER);
        Assert.fail(null == entity.getResourcesLevel(), ResultCode.INCORRECT_PARAMETER);

    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatchRoleResource(Role entity) {

        Assert.fail(null == entity, ResultCode.ID_REQUIRED);
        Assert.fail(null == entity.getId(), ResultCode.ID_REQUIRED);

        // 先清空同角色ID下数据
        boolean b = roleResourceService.removeByRoleId(entity.getId());

        boolean saveBatch = true;
        String resources = entity.getResources();
        if(null != resources && resources != "") {
            String[] resourcesArr = resources.split(",");

            // 再批量添加
            ArrayList<RoleResource> rrlist = new ArrayList<>();
            for (String item : resourcesArr) {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(entity.getId());
                roleResource.setResourceId(Long.valueOf(item));
                rrlist.add(roleResource);
            }
            saveBatch = roleResourceService.saveBatch(rrlist);
        }

        return saveBatch;
    }
}
