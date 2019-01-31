package com.lwx.mystory.model.entity;

import lombok.Data;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 17:24 2019/1/30
 **/
@Data
public class Log {
    private Integer id;
    private String action;
    private String data;
    private Integer authorId;
    private String ip;
    private Integer created;

}
