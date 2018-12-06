package com.lwx.mystory.service.impl;

import com.lwx.mystory.mapper.OptionMapper;
import com.lwx.mystory.model.entity.Option;
import com.lwx.mystory.service.IOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 19:33 2018/12/6
 **/
@Service
public class OptionServiceImpl implements IOptionService {

    @Autowired
    private OptionMapper optionMapper;

    @Override
    public Option getOptionByName(String name) {
        return optionMapper.getOptionByName(name);
    }

    @Override
    public List<Option> getOptions() {
        return null;
    }

    @Override
    public void saveOrUpdateOption(Map<String, String> options) {

    }

    @Override
    public void insertOption(String name, String value) {

    }
}
