package com.lwx.mystory.model.entity;

import lombok.Data;

/**
 * @Descripiton: 配置选项
 * @Author:linwx
 * @Date；Created in 19:25 2018/12/6
 **/
@Data
public class Option {

    //配置选项
    private String name;
    //配置值
    private String value;
    //配置描述
    private String description;
}
