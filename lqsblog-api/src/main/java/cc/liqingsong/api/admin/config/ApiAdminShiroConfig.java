package cc.liqingsong.api.admin.config;

import cc.liqingsong.api.admin.realm.ShiroAdminRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Admin Shiro 配置
 * @author liqingsong
 */
// @Configuration
public class ApiAdminShiroConfig {


    // 1、创建realm
    @Bean
    public ShiroAdminRealm shiroAdminRealm() {
        return new ShiroAdminRealm();
    }

    // 2、创建安全管理器
    @Bean
    public SecurityManager securityManager(ShiroAdminRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

    // 3、配置 Shiro 的过滤器工厂
    /**
     * 在web程序中，shiro 进行权限控制全部是通过一组过滤器集合进行控制
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        // 1、创建过滤器工厂
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 2、设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 3、通用设置（跳转登录页、未授权也...）
        shiroFilterFactoryBean.setLoginUrl("/common/err?code=10002"); // 设置未登录跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/common/err?code=10003"); // 设置未授权跳转
        // 4、设置过滤器集合

        /**
         * 设置所有的过滤器，有顺序的map
         *   key : 拦截的url地址
         *   value : 过滤器类型
         */
        Map<String,String> filterMap = new LinkedHashMap<>();

        // anon -- 匿名访问
        // filterMap.put("/common/**","anon"); // 当前请求地址可以匿名访问
        filterMap.put("/admin/v1/user/login","anon"); // 当前请求地址可以匿名访问

        // perms -- 具有某中权限(perms必须在authc前) (使用注解配置授权)
        // filterMap.put("/admin/v1/user/logout","perms[admin-v1-user-logout]");
        // filterMap.put("/admin/v1/user/info","perms[admin-info]");
        // filterMap.put("/admin/**","roles[系统管理员]");

        // authc -- 认证之后访问（登录）
        filterMap.put("/admin/**","authc"); // 当前请求地址必须认证后才可以访问



        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }


    // 4、开启Shiro 注解的支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator app=new DefaultAdvisorAutoProxyCreator();
        app.setProxyTargetClass(true);
        return app;
    }


}
