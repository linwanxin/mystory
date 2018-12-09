package com.lwx.mystory.service.impl;


import com.lwx.mystory.mapper.UserRoleMapper;
import com.lwx.mystory.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements IUserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void addRoleForUser(String userId, String roleId) {
        userRoleMapper.addRoleForUser(userId, roleId);
    }

}
