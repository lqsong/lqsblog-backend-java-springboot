package cc.liqingsong.service.pc.Impl;

import cc.liqingsong.database.entity.SinglePage;
import cc.liqingsong.database.mapper.SinglePageMapper;
import cc.liqingsong.service.pc.SinglePageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * SinglePage 服务实现类
 *
 * @author liqingsong
 */
@Service("pcSinglePageServiceImpl")
public class SinglePageServiceImpl extends ServiceImpl<SinglePageMapper, SinglePage> implements SinglePageService {

    @Override
    public SinglePage getByIdAndAddHit(Serializable id) {
        SinglePage info = super.getById(id);
        if (null == info) {
            return null;
        }

        SinglePage singlePage = new SinglePage();
        singlePage.setId(info.getId());
        // 浏览量 +1
        singlePage.setHit(info.getHit() + 1);

        this.updateById(singlePage);

        return info;
    }
}
