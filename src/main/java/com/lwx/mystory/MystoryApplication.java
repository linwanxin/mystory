package com.lwx.mystory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan("com.lwx.mystory.mapper")
@SpringBootApplication
@ServletComponentScan
public class MystoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MystoryApplication.class, args);
    }
}
