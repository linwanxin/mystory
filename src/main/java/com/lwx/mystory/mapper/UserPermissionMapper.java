package com.lwx.mystory.mapper;

import com.lwx.mystory.model.entity.Permission;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * description
 *
 * @author 70KG
 * @date 2018/9/3
 */

@Component
public interface UserPermissionMapper {

    List<Permission> findByUserName(String userName);

}
