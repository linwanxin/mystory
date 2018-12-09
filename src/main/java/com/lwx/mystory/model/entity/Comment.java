package com.lwx.mystory.model.entity;

import lombok.Data;

import java.util.List;

/**
 * @Descripiton:评论
 * @Author:linwx
 * @Date；Created in 17:26 2018/12/2
 **/
@Data
public class Comment {
    //主键
    private Integer coid;
    //post表关联，文章id
    private Integer cid;
    //创建时的时间戳
    private Integer created;
    //评论作者
    private String author;

    //评论所属用户ID
    private Integer author_id ;
    //评论所属内容作者id
    private Integer owner_id;

    //评论者邮件
    private String mail;
    //评论者网址
    private String url;
    //评论者ip地址
    private String ip;
    //评论者客户端
    private String agent;
    //评论内容
    private String content;
    //评论类型
    private String type;
    //评论状态
    private String status;
    //父级评论
    private Integer parent;

    private List<Comment> commentsList;
}
