package cc.liqingsong.service.pc;

import cc.liqingsong.database.entity.Topics;
import cc.liqingsong.database.entity.TopicsLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * TopicsLog 服务类
 *
 * @author liqingsong
 */
public interface TopicsLogService extends IService<TopicsLog> {


    /**
     * 插入一条专题、IP记录
     *
     * @param topics 专题
     * @param ip ip地址
     * @return
     */
    boolean saveTopicsIp(Topics topics, String ip);

}
