package cc.liqingsong.service.pc;

import cc.liqingsong.database.entity.SearchHotword;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * SearchHotword 服务类
 *
 * @author liqingsong
 */
public interface SearchHotwordService extends IService<SearchHotword> {


    /**
     * 插入一条关键词,存在则数量+1
     *
     * @param hotWord 关键词
     * @return
     */
    boolean saveHotWord(String hotWord);

    /**
     * 根据关键词查询信息
     *
     * @param name 关键词
     * @return
     */
    SearchHotword getByName(String name);


}
