package com.lwx.mystory.service;

import com.github.pagehelper.PageInfo;
import com.lwx.mystory.extension.Commons;
import com.lwx.mystory.model.entity.Comment;

import java.util.List;

/**
 * @Descripiton: 评论模块
 * @Author:linwx
 * @Date；Created in 10:57 2018/12/4
 **/
public interface ICommentService {
    /**
     * 查询全部评论数量
     *
     * @return
     */
    int getCommentCount();

    Comment getCommentById(Integer id);

    /**
     * 获取最新的评论
     *
     * @param page
     * @param limit
     * @return
     */
    PageInfo<Comment> getNewComments(Integer page, Integer limit);

    int saveComment(Comment comment);

    String delCommentById(Integer id);

    PageInfo<Comment> getCommentsByContentId(Integer contentId, Integer page, Integer limit);

    List<Comment> selectCommentsByAuthorId(Integer authorId);

    /**
     * 评论增加
     */
    String insertComment(Comment comment);
}
