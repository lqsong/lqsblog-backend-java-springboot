package cc.liqingsong.api.admin.config;

import cc.liqingsong.api.admin.interceptor.JwtAdminHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Admin Spring Mvc 配置
 * @author liqingsong
 */
@Configuration
public class ApiAdminMvcConfig implements WebMvcConfigurer {

/* 存 jwt 方式拦截器 S */
//    private JwtAdminHandlerInterceptor jwtAdminHandlerInterceptor;
//
//    @Autowired
//    public void setJwtAdminHandlerInterceptor(JwtAdminHandlerInterceptor jwtAdminHandlerInterceptor) {
//        this.jwtAdminHandlerInterceptor = jwtAdminHandlerInterceptor;
//    }
//
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        /* admin-api 拦截器 S */
//        // 1、添加自定义拦截器
//        InterceptorRegistration interceptorRegistration = registry.addInterceptor(jwtAdminHandlerInterceptor);
//        // 2、不拦截的url
//        /*
//        interceptorRegistration.excludePathPatterns("/static/**");
//        interceptorRegistration.excludePathPatterns("classpath:/static/");
//        interceptorRegistration.excludePathPatterns("/favicon.ico");
//        interceptorRegistration.excludePathPatterns("/error");
//        */
//        interceptorRegistration.excludePathPatterns("/admin/v1/user/login");
//        // 3、拦截的url
//        // interceptorRegistration.addPathPatterns("/**");
//        interceptorRegistration.addPathPatterns("/admin/**");
//
//        /* pc-api 拦截器 S */
//        // 1、添加自定义拦截器
//        // 2、不拦截的url
//        // 3、拦截的url
//
//        /* wx-api 拦截器 S */
//        // 1、添加自定义拦截器
//        // 2、不拦截的url
//        // 3、拦截的url
//    }

/* 存 jwt 方式拦截器 E */


}
