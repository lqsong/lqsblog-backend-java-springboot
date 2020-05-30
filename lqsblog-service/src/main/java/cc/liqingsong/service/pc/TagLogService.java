package cc.liqingsong.service.pc;

import cc.liqingsong.database.entity.TagLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * TagLog 服务类
 *
 * @author liqingsong
 */
public interface TagLogService extends IService<TagLog> {


    /**
     * 插入一条标签、IP记录
     *
     * @param tag 标签
     * @param ip ip地址
     * @return
     */
    boolean saveTagIp(String tag, String ip);

}
