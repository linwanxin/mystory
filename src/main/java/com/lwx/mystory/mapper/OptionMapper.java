package com.lwx.mystory.mapper;

import com.lwx.mystory.model.entity.Option;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 19:45 2018/12/6
 **/
@Component
public interface OptionMapper {
    Option getOptionByName(@Param("name") String name);

    List<Option> getOptions();

    void saveOption(Option option);

    void updateByName(Option option);
}
