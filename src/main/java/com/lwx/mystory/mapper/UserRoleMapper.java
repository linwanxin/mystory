package com.lwx.mystory.mapper;

import com.lwx.mystory.model.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * description
 *
 * @author 70KG
 * @date 2018/9/3
 */

@Component
public interface UserRoleMapper {

    List<Role> findByUserName(@Param("userName") String userName);

    void addRoleForUser(@Param("userId") String userId, @Param("roleId") String roleId);
}
