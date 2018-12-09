package com.lwx.mystory.service.impl;

import com.lwx.mystory.mapper.UserMapper;
import com.lwx.mystory.model.entity.Users;
import com.lwx.mystory.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 13:50 2018/12/7
 **/
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Users userLogin(String userName, String password) {
        return null;
    }

    @Override
    public void saveUser(Users user) {

    }

    @Override
    public Users getUserByUserName(String userName) {
        return null;
    }

    @Override
    public Users getUserById(Integer id) {
        return null;
    }
}
