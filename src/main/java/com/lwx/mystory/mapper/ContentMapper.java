package com.lwx.mystory.mapper;

import com.lwx.mystory.model.dto.Archive;
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

    /**
     * 查询归档文章
     * @return
     */
    List<Archive> selectArchive();

    /**
     * 根据特定条件查询文章
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    List<Content> getContentByConditions(@Param("type") String type,@Param("status") String status,@Param("startTime") Integer startTime,@Param("endTime") Integer endTime);

    /**
     * 根据文章ID获取文章
     * @param cid
     * @return
     */
    Content getContentById(@Param("cid") Integer cid);

    boolean updateContent(Content content);


}
