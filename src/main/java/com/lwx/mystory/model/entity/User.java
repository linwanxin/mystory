package com.lwx.mystory.model.entity;

import lombok.Data;

@Data
public class User extends BaseEntity {

    private Integer id;
    //用户名
    private String username;
    //用户密码
    private String password;
    //邮箱
    private String email;
    //用户主页
    private String home_url;
    //用户显示的名称
    private String screen_name;
    //用户注册时的时间戳
    private Integer created;
    //最后活动时间
    private Integer activated;
    //上次登陆最后活跃时间
    private Integer logged;
    //用户组
    private String group_name;
    //用户状态 0：锁定  1：正常
    private String status;


}
