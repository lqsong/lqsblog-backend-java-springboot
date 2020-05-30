package cc.liqingsong.service.pc;

import cc.liqingsong.database.entity.Config;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;


/**
 * Config 服务类
 *
 * @author liqingsong
 */
public interface ConfigService extends IService<Config> {

    /**
     * 获取配置
     */
    Map<String, String> getAll();

}
