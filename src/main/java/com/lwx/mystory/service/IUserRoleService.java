package com.lwx.mystory.service;

public interface IUserRoleService {

    /**
     * 为用户增加角色
     * @param userId
     * @param roleId
     */
    void addRoleForUser(String userId, String roleId);
}
