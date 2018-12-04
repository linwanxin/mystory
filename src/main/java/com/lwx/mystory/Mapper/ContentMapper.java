package com.lwx.mystory.Mapper;

import com.lwx.mystory.model.entity.Content;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 11:35 2018/12/2
 **/
@Component
public interface ContentMapper {

    /**
     * 根据类型查询文章
     * @param type
     * @param status
     * @return
     */
    List<Content> getContentsByType(@Param("type") String type,@Param("status") String status);
}
