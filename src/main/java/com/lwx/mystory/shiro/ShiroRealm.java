package com.lwx.mystory.shiro;

import com.lwx.mystory.constant.WebConstant;
import com.lwx.mystory.mapper.UserPermissionMapper;
import com.lwx.mystory.mapper.UserRoleMapper;
import com.lwx.mystory.model.entity.Permission;
import com.lwx.mystory.model.entity.Role;
import com.lwx.mystory.model.entity.User;
import com.lwx.mystory.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Descripiton:自定义shiro
 * @Author:linwx
 * @Date；Created in 10:35 2019/1/4
 **/
public class ShiroRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizingRealm.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserPermissionMapper userPermissionMapper;

    /**
     * 用户授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userName = user.getUsername();

        System.out.println("获取用户：" + userName + "的权限信息-----ShiroRealm.doGetAuthorizationInfo");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        //获取用户的角色信息
        List<Role> roleList = userRoleMapper.findByUserName(userName);
        Set<String> roleSet = new HashSet<>();
        for(Role r : roleList){
            roleSet.add(r.getName());
        }
        simpleAuthorizationInfo.setRoles(roleSet);

        //获取用户权限集
        List<Permission> permissionList = userPermissionMapper.findByUserName(userName);
        Set<String> permissionSet = new HashSet<>();
        for(Permission p : permissionList){
            permissionSet.add(p.getDescription());
        }
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 登陆认证,token是前台构造的的用户名和密码的token，继承来的
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        //这里不转成char会报错。。
        String password = new String((char[]) authenticationToken.getCredentials());
        logger.info("用户："+userName+ "进行登录认证---ShiroRealm.doGetAuthenticationInfo");
        User user = userService.getUserByUserName(userName);
        if(user == null){
            logger.error("用户：" + userName + "登录认证失败，原因：用户不存在");
            throw new UnknownAccountException("用户不存在或密码错误！");
        }
        if(!password.equals(user.getPassword())){
            logger.error("用户：" + userName + "登录认证失败，原因：密码错误！");
            throw new IncorrectCredentialsException("用户不存在或密码错误！");
        }
        if(WebConstant.STATUS_0.equals(user.getStatus())){
            logger.error("用户：" + userName + "登录认证失败，原因：账号已被锁定！");
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,password,getName());
        return info;
    }
}
