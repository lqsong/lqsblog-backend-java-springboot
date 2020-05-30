package cc.liqingsong.service.pc;

import cc.liqingsong.database.entity.SearchHotwordLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * SearchHotwordLog 服务类
 *
 * @author liqingsong
 */
public interface SearchHotwordLogService extends IService<SearchHotwordLog> {

    /**
     * 插入一条关键词、IP记录
     *
     * @param hotWord 关键词
     * @param Ip ip地址
     * @return
     */
    boolean saveHotWordIp(String hotWord, String Ip);
}
