package cc.liqingsong.service.admin;

import cc.liqingsong.database.dto.admin.ConfigDTO;
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
     * 修改配置
     *
     * @param configDTO 实体类对象
     */
    boolean updateConfigDTO(ConfigDTO configDTO) throws IllegalAccessException;

    /**
     * 获取配置
     */
    ConfigDTO getConfigDTO() throws IllegalAccessException;

    /**
     * 修改配置
     *
     * @param map  数据 Map<字段,字段值>
     */
    boolean updateAll(Map<String, String> map);

    /**
     * 获取配置
     */
    Map<String, String> getAll();
}
