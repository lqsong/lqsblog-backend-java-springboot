package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.database.dto.admin.ConfigDTO;
import cc.liqingsong.database.entity.Config;
import cc.liqingsong.database.mapper.ConfigMapper;
import cc.liqingsong.service.admin.ConfigService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Config 服务实现类
 *
 * @author liqingsong
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Override
    public boolean updateConfigDTO(ConfigDTO configDTO) throws IllegalAccessException {
        for (Field field: configDTO.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            // System.out.println(field.getName() + ":" + field.get(configDTO) );
            UpdateWrapper<Config> uw = new UpdateWrapper<>();
            uw.set("content",field.get(configDTO));
            uw.eq("name",field.getName());
            super.update(uw);
        }
        return true;
    }

    @Override
    public ConfigDTO getConfigDTO() throws IllegalAccessException {
        ConfigDTO configDTO = new ConfigDTO();
        List<Config> list = super.list();
        Map<String, String> vlist = new HashMap<>();
        for (Config item: list) {
            vlist.put(item.getName(), item.getContent());
        }
        for (Field field: configDTO.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            // System.out.println(field.getName() + ":" + field.get(configDTO) );
            field.set(configDTO,vlist.get(field.getName()));
        }

        return configDTO;
    }

    @Override
    public boolean updateAll(Map<String, String> map) {
        for (String key : map.keySet()) {
            UpdateWrapper<Config> uw = new UpdateWrapper<>();
            uw.set("content",map.get(key));
            uw.eq("name",key);
            super.update(uw);
        }
        return true;
    }

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
