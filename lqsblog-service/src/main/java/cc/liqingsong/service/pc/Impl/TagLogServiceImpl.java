package cc.liqingsong.service.pc.Impl;

import cc.liqingsong.database.entity.TagLog;
import cc.liqingsong.database.mapper.TagLogMapper;
import cc.liqingsong.service.pc.TagLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * TagLog 服务实现类
 *
 * @author liqingsong
 */
@Service("pcTagLogServiceImpl")
public class TagLogServiceImpl extends ServiceImpl<TagLogMapper, TagLog> implements TagLogService {
    @Override
    public boolean saveTagIp(String tag, String ip) {
        if (null == tag || null == ip) {
            return false;
        }

        TagLog entity = new TagLog();
        entity.setTag(tag);
        entity.setIp(ip);
        entity.setCreateTime(LocalDateTime.now());
        return super.save(entity);
    }
}
