package com.lwx.mystory.Mapper;

import com.lwx.mystory.model.entity.Meta;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 22:22 2018/12/5
 **/
@Component
public interface MetaMapper {
    Meta getMate(@Param("type") String type,@Param("name") String name);
}
