package com.lwx.mystory.model.entity;

import lombok.Data;

@Data
public class Permission  extends  BaseEntity{

    private Integer id;

    private String url;
    /**url描述**/
    private String description;
}
