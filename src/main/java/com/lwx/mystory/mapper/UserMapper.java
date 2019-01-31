package com.lwx.mystory.mapper;

import com.lwx.mystory.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 13:58 2018/12/7
 **/
@Component
public interface UserMapper {

    User getUserById(@Param("id") Integer id);

    void saveUser(User user);

    User getUserByUserName(@Param("userName") String userName);

    boolean updateUser(User user);
}
