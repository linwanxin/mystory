package com.lwx.mystory.model.entity;

import lombok.Data;

/**
 * @Descripiton: 友情连接的元数据
 * @Author:linwx
 * @Date；Created in 23:44 2018/12/4
 **/
@Data
public class Meta {

    private Integer mid ;

    private String name;
    //项目缩略名
    private String slug;
    //项目类型
    private String type;
    //
    private String description;
    //排序
    private Integer sort;
    //父级
    private Integer parent;

}
