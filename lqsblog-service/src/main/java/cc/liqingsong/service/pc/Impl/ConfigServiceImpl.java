package cc.liqingsong.service.pc.Impl;

import cc.liqingsong.database.entity.Config;
import cc.liqingsong.database.mapper.ConfigMapper;

import cc.liqingsong.service.pc.ConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Config 服务实现类
 *
 * @author liqingsong
 */
@Service("pcConfigServiceImpl")
public class ConfigServiceImpl  extends ServiceImpl<ConfigMapper, Config> implements ConfigService {


    @Override
    public Map<String, String> getAll() {
        Map<String, String> map = new HashMap<>();
        List<Config> list = super.list();
        for (Config item: list) {
            map.put(item.getName(),item.getContent());
        }
        return map;
    }
}


