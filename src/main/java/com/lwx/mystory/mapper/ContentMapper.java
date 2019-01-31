package com.lwx.mystory.mapper;

import com.lwx.mystory.model.dto.Archive;
import com.lwx.mystory.model.dto.Types;
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
     *
     * @param type
     * @param status
     * @return
     */
    List<Content> getContentsByType(@Param("type") String type, @Param("status") String status);

    /**
     * 查询归档文章
     *
     * @return
     */
    List<Archive> getArchives(@Param("type") String type, @Param("status") String status);

    /**
     * 根据特定条件查询文章
     *
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    List<Content> getContentsByConditions(@Param("type") String type, @Param("status") String status, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime);

    /**
     * 根据文章ID获取文章
     *
     * @param cid
     * @return
     */
    Content getContentById(@Param("cid") Integer cid);

    boolean updateContent(Content content);

    Content getContentBySlug(@Param("slug") String slug);


    /**
     * 根据mid联合relationship表来查询标签下的所有文章
     */
    List<Content> getContentsByMetaId(@Param("mid") Integer mid);

    /**
     * 根据文章标题搜索文章
     * @param title
     * @param type
     * @param status
     */
    List<Content> getContentsByTitle(@Param("title") String title, @Param("type") String type, @Param("status") String status);

    /**
     * 根据slug和type来查询文章的个数
     */
    long getContentsBySlugType(@Param("type") String type,@Param("slug") String slug);


    /**
     * 保存文章
     */
    void saveContent(Content content);
    /**
     * 根据userID查询
     */
    List<Content> getContentsByUserId(@Param("type") String type,@Param("userId") Integer userId);
    /**
     * 根据Id删除文章
     */
    void deleteContentById(@Param("id") Integer id);

}
