package com.lwx.mystory.Mapper;

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
}
