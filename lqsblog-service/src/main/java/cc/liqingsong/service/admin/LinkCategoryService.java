package cc.liqingsong.service.admin;

import cc.liqingsong.database.entity.LinkCategory;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * LinkCategory 服务类
 *
 * @author liqingsong
 */
public interface LinkCategoryService extends IService<LinkCategory> {

    /**
     * 列表
     *
     * @param sort 排序字段[id,sort]
     * @param order [desc 降序，asc 升序]
     * @return
     */
    List<LinkCategory> list(Integer sort, Integer order);

}
