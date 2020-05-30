package cc.liqingsong.service.admin;

import cc.liqingsong.database.entity.SearchHotword;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * SearchHotword 服务类
 *
 * @author liqingsong
 */
public interface SearchHotwordService extends IService<SearchHotword> {


    /**
     *  Hit降序 分页列表
     *
     * @param page 分页对象
     * @return
     */
    IPage<SearchHotword> pageOrderHitDesc(IPage<SearchHotword> page);



}
