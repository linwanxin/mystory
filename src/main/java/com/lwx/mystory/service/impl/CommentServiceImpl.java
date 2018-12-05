package com.lwx.mystory.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwx.mystory.Mapper.CommentMapper;
import com.lwx.mystory.model.entity.Comment;
import com.lwx.mystory.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 11:12 2018/12/4
 **/
@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public int getCommentCount() {
        return 0;
    }

    @Override
    public Comment getCommentById(Integer id) {
        return null;
    }

    @Override
    public PageInfo<Comment> getNewComments(Integer page, Integer limit) {
        return null;
    }

    @Override
    public int saveComment(Comment comment) {
        return 0;
    }

    @Override
    public String delCommentById(Integer id) {
        return null;
    }

    @Override
    public PageInfo<Comment> getCommentsByContentId(Integer contentId, Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<Comment> comments =  commentMapper.getCommentsByContentId(contentId,"approved");
        PageInfo<Comment> commentPageInfo = new PageInfo(comments);
        return commentPageInfo;
    }
}
