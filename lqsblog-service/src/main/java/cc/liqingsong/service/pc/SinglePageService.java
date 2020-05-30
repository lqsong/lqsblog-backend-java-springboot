package cc.liqingsong.service.pc;

import cc.liqingsong.database.entity.SinglePage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * SinglePage 服务类
 *
 * @author liqingsong
 */
public interface SinglePageService extends IService<SinglePage> {

    /**
     * 根据id查找详情 ，并添加浏览量
     *
     * @param id ID
     * @return
     */
    SinglePage getByIdAndAddHit(Serializable id);

}
