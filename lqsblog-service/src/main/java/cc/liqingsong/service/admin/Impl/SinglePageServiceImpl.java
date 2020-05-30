package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.Link;
import cc.liqingsong.database.entity.SinglePage;
import cc.liqingsong.database.mapper.SinglePageMapper;
import cc.liqingsong.service.admin.SinglePageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * SinglePage 服务实现类
 *
 * @author liqingsong
 */
@Service
public class SinglePageServiceImpl  extends ServiceImpl<SinglePageMapper, SinglePage> implements SinglePageService {

    /**
     * 根据 ID 修改
     *
     * @param entity 实体类对象
     */
    @Override
    public boolean updateById(SinglePage entity) {

        if (null == entity) {
            return false;
        }

        Assert.fail(null == entity.getId(), ResultCode.ID_NOT_FOUND);

        return super.updateById(entity);
    }
}
