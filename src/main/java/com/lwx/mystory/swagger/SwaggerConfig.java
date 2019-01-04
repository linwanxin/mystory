package com.lwx.mystory.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Descripiton: swagger配置类
 * @Author:linwx
 * @Date；Created in 9:50 2018/12/31
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket buildDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lwx.mystory.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    public ApiInfo buildApiInf(){
        return new ApiInfoBuilder()
                .title("系统RESTful API文档")
                .contact(new Contact("yulenka","http://www.yulenka.com","linwx18chinaunicom.cn"))
                .version("1.0")
                .build();
    }
}
