package com.lwx.mystory.shiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * @Descripiton:Shiro配置
 * @Author:linwx
 * @Date；Created in 10:13 2019/1/4
 **/
@Configuration
public class ShiroConfiguration {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //
        shiroFilterFactoryBean.setLoginUrl("/admin/login");
        shiroFilterFactoryBean.setSuccessUrl("/admin/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");


        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 静态资源不拦截
        filterChainDefinitionMap.put("/backadmin/**", "anon");
        filterChainDefinitionMap.put("/user/**", "anon");

        // 后台页面不拦截
        filterChainDefinitionMap.put("/admin/registry", "anon");
        // 前台页面不拦截
        filterChainDefinitionMap.put("/article/**", "anon");
        filterChainDefinitionMap.put("/about/**", "anon");
        filterChainDefinitionMap.put("/page/**", "anon");
        filterChainDefinitionMap.put("/links/**", "anon");
        filterChainDefinitionMap.put("/category/**", "anon");
        filterChainDefinitionMap.put("/archives/**", "anon");
        filterChainDefinitionMap.put("/search/**", "anon");
        filterChainDefinitionMap.put("/tag/**", "anon");
        filterChainDefinitionMap.put("/comment/**", "anon");
        filterChainDefinitionMap.put("/soulPainter.html/**", "anon");
        filterChainDefinitionMap.put("/custom/**", "anon");

        filterChainDefinitionMap.put("/druid/**", "anon");

        filterChainDefinitionMap.put("/", "anon");

        //除以上所有的url都必须认证才能通过才可以访问，未通过认证自动
        filterChainDefinitionMap.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(){
        //配置SecurityManager
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //将remeberMe交给securityManager管理
        securityManager.setRememberMeManager(cookieRememberMeManager());
        //Realm设置管理
        securityManager.setRealm(shiroRealm());

        return securityManager;

    }

    @Bean
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        return shiroRealm;
    }

    /**
     * 创建cookie管理对象
     */
    public CookieRememberMeManager cookieRememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(remeberMeCookie());
        //RemeberMe cookie 加密的密钥
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }
    /**
     * cookie对象
     */
    public SimpleCookie remeberMeCookie(){
        SimpleCookie cookie = new SimpleCookie("remeber_me");
        //设置cookie过期时间,单位为秒，5天！
        cookie.setMaxAge(5 * 86400);
        return cookie;
    }
}
