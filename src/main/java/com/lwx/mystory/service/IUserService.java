package com.lwx.mystory.service;

import com.lwx.mystory.model.entity.User;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 10:45 2018/12/7
 **/
public interface IUserService {


    User userLogin(String userName, String password);

    void saveUser(User user);


    User getUserByUserName(String userName);

    User getUserById(Integer id);

    //更新用户信息
    boolean updateUser(User user);

}
