package com.lwx.mystory.interceptor;

import com.lwx.mystory.utils.TaleUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;

/**
 * @Descripiton: 拦截器实现，2.0推荐直接实现！
 * @Author:linwx
 * @Date；Created in 22:06 2018/12/6
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private BaseInterceptor baseInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(baseInterceptor);
    }

    /**
     * 添加静态资源文件，外部可以直接访问地址
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + TaleUtils.getUplodFilePath() + "upload/");
        //super.addResourceHandlers(registry);
    }

}
