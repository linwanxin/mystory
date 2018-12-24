package com.lwx.mystory.model.entity;

import lombok.Data;

/**
 * @Author:linwx
 * @Date；Created in 10:24 2018/12/2
 * 文章实体
 **/
@Data
public class Content {
    private Integer cid;
    //内容标题
    private String title;
    //内容缩略名
    private String slug;
    //创建时的unix时间戳
    private Integer created;
    //修改时的时间戳
    private Integer modified;
    //内容
    private String content;
    //作者ID
    private Integer authorId;
    //点击次数
    private Integer hits;
    //类别
    private String type;
    //内容类型 markdown或者html
    private String fmtType;
    //文章缩略图
    private String thumbImg;
    //标签列表
    private String tags;
    //分类列表
    private String categories;
    //内容状态
    private String status;
    //评论数
    private Integer commentsNum;
    //是否允许评论
    private Integer allowComment;
    //是否允许ping
    private Integer allowPing;
    //允许在聚合中
    private Integer allowFeed;


}
