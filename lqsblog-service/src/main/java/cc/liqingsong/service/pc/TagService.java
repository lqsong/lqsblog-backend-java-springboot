package cc.liqingsong.service.pc;

import cc.liqingsong.database.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Tag 服务类
 *
 * @author liqingsong
 */
public interface TagService extends IService<Tag> {

    /**
     * 根据名称查询信息
     *
     * @param name 名称
     * @return
     */
    Tag getByName(String name);

    /**
     * 根据名称查询信息，并添加浏览量
     *
     * @param name 名称
     * @return
     */
    Tag getByNameAndAddHit(String name);
}
