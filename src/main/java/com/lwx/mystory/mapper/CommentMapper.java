package com.lwx.mystory.mapper;

import com.lwx.mystory.model.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 11:13 2018/12/4
 **/
@Component
public interface CommentMapper {
    List<Comment> getCommentsByContentId(@Param("cid") Integer cid, @Param("status") String status);

    /**
     * Description:查询非己评论
     * Author:70kg
     * Param [authorId]
     * Return java.util.List<com.nmys.story.model.entity.Comments>
     * Date 2018/5/10 14:14
     */
    List<Comment> selectCommentsByAuthorId(@Param("authorId") Integer authorId);
}
