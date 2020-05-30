package cc.liqingsong.service.pc.Impl;

import cc.liqingsong.database.entity.Topics;
import cc.liqingsong.database.entity.TopicsLog;
import cc.liqingsong.database.mapper.TopicsLogMapper;
import cc.liqingsong.service.pc.TopicsLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * TopicsLog 服务实现类
 *
 * @author liqingsong
 */
@Service("pcTopicsLogServiceImpl")
public class TopicsLogServiceImpl extends ServiceImpl<TopicsLogMapper, TopicsLog> implements TopicsLogService {

    @Override
    public boolean saveTopicsIp(Topics topics, String ip) {
        if (null == topics || null == ip) {
            return false;
        }

        TopicsLog entity = new TopicsLog();
        entity.setTid(topics.getId());
        entity.setTopics(topics.getTitle());
        entity.setIp(ip);
        entity.setCreateTime(LocalDateTime.now());
        return super.save(entity);
    }
}
