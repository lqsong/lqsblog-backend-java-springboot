package cc.liqingsong.api.admin.config;

import cc.liqingsong.api.admin.realm.JwtDefaultSubjectFactory;
import cc.liqingsong.api.admin.realm.JwtFilter;
import cc.liqingsong.api.admin.realm.JwtShiroAdminRealm;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Admin JWTShiro 配置
 * @author liqingsong
 */
@Configuration
@Setter
@Getter
public class ApiAdminJwtShiroConfig {

    // Header头 Token 名称
    @Value("${lqsblog.jwt.header.tokenkey}")
    private String tokenkey;

    // 设置允许跨域的源 - application 中配置
    @Value("${lqsblog.access.origins}")
    private String[] origins;

    // 未登陆返回
    private String loginUrl = "/common/err?code=10002";

    // 没有权限返回
    private String unauthorizedUrl = "/common/err?code=10003";

    /**
     * a. 告诉shiro不要使用默认的DefaultSubject创建对象，因为不能创建Session
     */
    @Bean
    public SubjectFactory subjectFactory() {
        return new JwtDefaultSubjectFactory();
    }

    // 1、创建realm
    @Bean
    public JwtShiroAdminRealm jwtShiroAdminRealm() {
        return new JwtShiroAdminRealm();
    }


    // 2、创建安全管理器
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager (JwtShiroAdminRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 使用自己的realm
        securityManager.setRealm(realm);
        /*
         * b、关闭 ShiroDAO 功能
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        // 不需要将 Shiro Session 中的东西存到任何地方（包括 Http Session 中）
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        //禁止Subject的getSession方法
        securityManager.setSubjectFactory(subjectFactory());

        return securityManager;
    }

    // 3、配置 Shiro 的过滤器工厂
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        // 1、创建过滤器工厂
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 2、设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 3、通用设置（跳转登录页、未授权也...）
        shiroFilterFactoryBean.setLoginUrl(loginUrl); // 设置未登录跳转
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl); // 设置未授权跳转

        /*
         * c. 添加jwt过滤器，并在下面注册
         * 也就是将jwtFilter注册到shiro的Filter中
         * 指定除了login之外的请求都先经过jwtFilter
         */
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new JwtFilter(tokenkey,loginUrl, origins));
        shiroFilterFactoryBean.setFilters(filterMap);

        /*
         * 自定义url规则-拦截器
         * http://shiro.apache.org/web.html#urls-
         */
        Map<String, String> filterRuleMap = new HashMap<>();


        // anon -- 匿名访问(不用登录和权限) /admin/v1/user/login => /admin/v1/user/logi** , 不这样写权限级别没有/admin/**高
        filterRuleMap.put("/admin/v1/user/logi**", "anon");
        filterRuleMap.put("/admin/v1/guest/**", "anon");

        // perms -- 具有某中权限(perms必须在authc前) (jwt必须使用注解配置授权)
        // filterRuleMap.put("/admin/v1/user/logout","perms[/admin/v1/user/logout]");

        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/admin/**", "jwt");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);

        return shiroFilterFactoryBean;
    }


    /**
     * 4、开启Shiro 注解的支持
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    /**
     * 开启shiro注解模式
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator app=new DefaultAdvisorAutoProxyCreator();
        app.setProxyTargetClass(true);
        return app;
    }
    /**
     * 管理shiro bean生命周期
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }






}
