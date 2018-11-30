package com.lwx.mystory.model.entity;

import lombok.Data;

/**
 *用户角色类
 */
@Data
public class Role extends BaseEntity {

    private Integer id;

    private String name;

    private String description;
}
