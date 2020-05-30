package cc.liqingsong.service.pc.Impl;

import cc.liqingsong.database.entity.SearchHotwordLog;
import cc.liqingsong.database.mapper.SearchHotwordLogMapper;
import cc.liqingsong.service.pc.SearchHotwordLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * SearchHotwordLog 服务实现类
 *
 * @author liqingsong
 */
@Service("pcSearchHotwordLogServiceImpl")
public class SearchHotwordLogServiceImpl  extends ServiceImpl<SearchHotwordLogMapper, SearchHotwordLog> implements SearchHotwordLogService {

    @Override
    public boolean saveHotWordIp(String hotWord, String Ip) {
        if (null == hotWord || null == Ip) {
            return false;
        }

        SearchHotwordLog entity = new SearchHotwordLog();
        entity.setHotword(hotWord);
        entity.setIp(Ip);
        entity.setCreateTime(LocalDateTime.now());
        return super.save(entity);
    }
}
