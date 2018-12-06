package com.lwx.mystory.interceptor;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 22:06 2018/12/6
 **/
@Component
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Resource
    private BaseInterceptor baseInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(baseInterceptor);
    }


}
