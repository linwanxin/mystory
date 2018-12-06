package com.lwx.mystory.service;

import com.lwx.mystory.model.entity.Option;

import java.util.List;
import java.util.Map;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 19:29 2018/12/6
 **/
public interface IOptionService {
    /**
     * 根据名字查询配置
     * @param name
     * @return
     */
    Option getOptionByName(String name);

    /**
     * 获取所有的配置
     * @return
     */
    List<Option> getOptions();

    void saveOrUpdateOption(Map<String,String> options);

    void insertOption(String name,String value);



}
