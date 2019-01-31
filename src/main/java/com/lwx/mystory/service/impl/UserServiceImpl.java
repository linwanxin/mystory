package com.lwx.mystory.service.impl;

import com.lwx.mystory.mapper.UserMapper;
import com.lwx.mystory.model.entity.User;
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
    public User userLogin(String userName, String password) {
        return null;
    }

    @Override
    public void saveUser(User user) {
    }

    /**
     * 通过用户名获取用户
     * @param userName
     * @return
     */
    @Override
    public User getUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }

    @Override
    public User getUserById(Integer id) {
        return null;
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public boolean updateUser(User user){
        return userMapper.updateUser(user);
    }

}
