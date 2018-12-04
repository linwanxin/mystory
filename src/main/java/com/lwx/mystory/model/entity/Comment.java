package com.lwx.mystory.model.entity;

import lombok.Data;

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


}
