package com.lwx.mystory.model.entity;

import lombok.Data;

/**
 *用户角色类
 */
@Data
public class Role extends BaseEntity {

    private Integer id;

    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色描述
     */
    private String description;
}
