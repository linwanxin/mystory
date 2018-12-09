package com.lwx.mystory.service;

import com.lwx.mystory.model.entity.Users;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 10:45 2018/12/7
 **/
public interface IUserService {


    Users userLogin(String userName,String password);

    void saveUser(Users user);


    Users getUserByUserName(String userName);

    Users getUserById(Integer id);
}
