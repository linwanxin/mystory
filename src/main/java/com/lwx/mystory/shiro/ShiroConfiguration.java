package com.lwx.mystory.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;

/**
 * @Descripiton: shiro总配置
 * @Author:linwx
 * @Date；Created in 10:28 2018/12/7
 **/
@Configuration
public class ShiroConfiguration {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //登录的url
        shiroFilterFactoryBean.setLoginUrl("/admin/login");
        //登陆成功后跳转的url
        shiroFilterFactoryBean.setSuccessUrl("/admin/index");
        //未授权url
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        LinkedHashMap<String,String> filterChainDefinitionMap = new LinkedHashMap<>();

        //静态资源不拦截
        filterChainDefinitionMap.put("/backadmin/**","anon");
        filterChainDefinitionMap.put("/user/**","anon");

        //后台页面不拦截
        filterChainDefinitionMap.put("/admin/registry","anon");
        //前台页面不拦截
        filterChainDefinitionMap.put("/article/**","anon");
        filterChainDefinitionMap.put("/about/**","anon");
        filterChainDefinitionMap.put("/page/**","anon");
        filterChainDefinitionMap.put("/links/**","anon");
        filterChainDefinitionMap.put("/category/**","anon");
        filterChainDefinitionMap.put("/archives/**","anon");
        filterChainDefinitionMap.put("/search/**","anon");
        filterChainDefinitionMap.put("/tag/**","anon");
        filterChainDefinitionMap.put("/comment/**","anon");
        filterChainDefinitionMap.put("soulPainter.html/**","anon");
        filterChainDefinitionMap.put("/custom/**","anon");

        // druid数据源监控页面不拦截
        filterChainDefinitionMap.put("/druid/**", "anon");

        filterChainDefinitionMap.put("/", "anon");
        // 除上以外所有url都必须认证通过才可以访问，未通过认证自动访问LoginUrl
        filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //将realm交给SecurityManager管理
        securityManager.setRealm(shiroRealm());

        return securityManager;

    }

    @Bean
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        return shiroRealm;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        // Shiro生命周期处理器
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    /**
     * thymeleaf-shiro标签的使用
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }


}
