package com.lwx.mystory.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwx.mystory.constant.WebConstant;
import com.lwx.mystory.mapper.CommentMapper;
import com.lwx.mystory.mapper.ContentMapper;
import com.lwx.mystory.model.entity.Comment;
import com.lwx.mystory.model.entity.Content;
import com.lwx.mystory.service.ICommentService;
import com.lwx.mystory.service.IContentService;
import com.lwx.mystory.utils.DateKit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private IContentService contentService;

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
        PageHelper.startPage(page, limit);
        List<Comment> comments = commentMapper.getCommentsByContentId(contentId, "approved");
        PageInfo<Comment> commentPageInfo = new PageInfo(comments);
        return commentPageInfo;
    }

    @Override
    public List<Comment> selectCommentsByAuthorId(Integer authorId) {
        return commentMapper.selectCommentsByAuthorId(authorId);
    }


    @Transactional
    public String insertComment(Comment comment){
        if (StringUtils.isBlank(comment.getAuthor())) {
            comment.setAuthor("热心网友");
        }

        Content content = contentMapper.getContentById(comment.getCid());
        if(content == null){
            return "不存在的文章";
        }
        //文章创作者的ID！
        comment.setOwner_id(content.getAuthorId());
        comment.setStatus("not_audit");
        comment.setCreated(DateKit.getCurrentUnixTime());
        //非admin用户的评论都是0,用来做区分
        comment.setAuthor_id(0);
        commentMapper.saveComment(comment);

        //更新文章下的评论数目
        Content temp = new Content();
        temp.setCid(content.getCid());
        temp.setCommentsNum(content.getCommentsNum() + 1);
        contentService.updateContent(temp);

        return WebConstant.SUCCESS_RESULT;
    }


    @Override
    public PageInfo<Comment> getRecentComments(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<Comment> comments = commentMapper.getRecentComments();
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        return pageInfo;
    }
}
