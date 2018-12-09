package com.lwx.mystory.mapper;

import com.lwx.mystory.model.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 13:58 2018/12/7
 **/
@Component
public interface UserMapper {

    Users getUserById(@Param("id") Integer id);

    void saveUser(Users user);

    Users getUserByUserName(@Param("userName") String userName);
}
