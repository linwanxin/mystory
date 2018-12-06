package com.lwx.mystory.model.dto;

import com.lwx.mystory.model.entity.Content;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Descripiton: 文章归档
 * @Author:linwx
 * @Date；Created in 23:50 2018/12/5
 **/
@Data
public class Archive implements Serializable {

    private static final long serialVersionUID = 1L;

    private String date_str;

    //文章日期
    private String date;
    //文章数量
    private String count;
    //文章集合
    private List<Content> articles;

}
