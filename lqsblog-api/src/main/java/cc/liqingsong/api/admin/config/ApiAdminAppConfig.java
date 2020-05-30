package cc.liqingsong.api.admin.config;

import cc.liqingsong.api.admin.interceptor.JwtAdminHandlerInterceptor;
import cc.liqingsong.common.utils.IdWorker;
import cc.liqingsong.common.utils.JwtUtils;
import cc.liqingsong.common.utils.Upload;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Admin Api App 配置
 * @author liqingsong
 */
@Configuration
public class ApiAdminAppConfig {

    @Bean
    public JwtAdminHandlerInterceptor jwtAdminHandlerInterceptor() {
        return new JwtAdminHandlerInterceptor();
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }

    @Bean
    public Upload upload() {
        return new Upload();
    }
}

