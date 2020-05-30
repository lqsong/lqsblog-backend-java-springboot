package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.Link;
import cc.liqingsong.database.entity.LinkCategory;
import cc.liqingsong.database.enums.LinkCode;
import cc.liqingsong.database.mapper.LinkCategoryMapper;
import cc.liqingsong.service.admin.LinkCategoryService;
import cc.liqingsong.service.admin.LinkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * LinkCategory 服务实现类
 *
 * @author liqingsong
 */
@Service
public class LinkCategoryServiceImpl  extends ServiceImpl<LinkCategoryMapper, LinkCategory> implements LinkCategoryService {

    private LinkService linkService;
    @Autowired
    @Lazy
    public void setLinkService(LinkService linkService) {
        this.linkService = linkService;
    }

    /**
     * 排序字段
     */
    private String[] sort = new String[]{"id","sort"};
    public String getSort(int i ) {
        int length = sort.length - 1;
        return i > length ? sort[0] : sort[i];
    }

    /**
     * 插入一条记录
     *
     * @param entity 实体类对象
     */
    @Override
    public boolean save(LinkCategory entity) {
        if (null == entity) {
            return false;
        }

        saveUpdateVerify(entity, "save");

        return super.save(entity);
    }

    /**
     * 根据 ID 删除
     *
     * @param id ID
     */
    @Override
    public boolean removeById(Serializable id) {
        QueryWrapper<Link> qw = new QueryWrapper<>();
        qw.eq("category_id",id);
        int count = linkService.count(qw);
        Assert.fail( count > 0, LinkCode.CATEGORY_ID_LINK_DATA);

        return super.removeById(id);
    }

    /**
     * 根据 ID 修改
     *
     * @param entity 实体类对象
     */
    @Override
    public boolean updateById(LinkCategory entity) {

        if (null == entity) {
            return false;
        }

        Assert.fail(null == entity.getId(), ResultCode.ID_NOT_FOUND);

        saveUpdateVerify(entity, "update");

        return super.updateById(entity);
    }




    /**
     * 私用验证
     *
     * @param entity 实体类对象
     * @param type 类型  save、update
     */
    private void saveUpdateVerify(LinkCategory entity, String type) {

        Assert.fail(null == entity.getName(), ResultCode.NAME_NOT_EMPTY);
        int nameLen = entity.getName().length();
        Assert.fail(nameLen > 8 || nameLen < 1, LinkCode.CATEGORY_NAME_LENGTH_WORDS);

        Assert.fail(null == entity.getAlias(), LinkCode.CATEGORY_ALIAS_NOT_EMPTY);
        int aliasLen = entity.getAlias().length();
        Assert.fail(aliasLen > 10 || aliasLen < 1, LinkCode.CATEGORY_ALIAS_LENGTH_WORDS);


        QueryWrapper<LinkCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("alias",entity.getAlias());
        switch(type) {
            case "update" :
                queryWrapper.ne("id", entity.getId());
                break;
            default :
        }
        int count = super.count(queryWrapper);
        Assert.fail( count > 0, LinkCode.CATEGORY_ALIAS_THE_SAME);

    }


    @Override
    public List<LinkCategory> list(Integer sort, Integer order) {
        QueryWrapper<LinkCategory> qw = new QueryWrapper<>();
        String sortStr = sort == null ? getSort(0) : getSort(sort);
        if (null != order && order == 1) {
            qw.orderByAsc(sortStr);
        } else {
            qw.orderByDesc(sortStr);
        }

        return super.list(qw);
    }
}
