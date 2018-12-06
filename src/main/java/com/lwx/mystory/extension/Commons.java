package com.lwx.mystory.extension;

import com.lwx.mystory.model.entity.Option;
import com.lwx.mystory.service.IOptionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @Descripiton:公共主题函数
 * @Author:linwx
 * @Date；Created in 15:57 2018/12/6
 **/
@Component
public class Commons {
    public static String THEME  = "themes/front";

    private static IOptionService optionService;

    /**
     * 网站配置项
     * @param key
     * @return
     */
    public static String site_option(String key){
        return site_option(key,"");
    }
    public static String site_option(String key,String defaultValue){
        if(StringUtils.isBlank(key)){
            return "";
        }
        //从数据库读取
        Option option = optionService.getOptionByName(key);
        String str = option.getValue();
        if(StringUtils.isNotBlank(str)){
            return str;
        }else{
            return defaultValue;
        }
    }
}
