package com.lwx.mystory.controller.admin;

import com.lwx.mystory.controller.BaseController;
import com.lwx.mystory.model.bo.RestResponseBo;
import com.lwx.mystory.service.IUserService;
import com.lwx.mystory.utils.MD5Utils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 10:19 2018/12/31
 **/
@Controller
@RequestMapping("/admin")
public class AuthController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    @ApiOperation(value="后台登陆页面",notes = "后台登陆页面初始化")
    public String login(){
        return "admin/login";
    }

    @PostMapping("/login")
    @ApiOperation(value ="后台登陆提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",required = true,dataType = "String"),
            @ApiImplicitParam(name = "password",value = "用户名",required = true,dataType = "String"),
            @ApiImplicitParam(name = "remeber_me",value = "记住我",required = true,dataType = "String")
    })
    @ResponseBody
    public RestResponseBo doLogin(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam(required = false) String remeber_me){
        //MD5加密
        password = MD5Utils.encrypt(username,password);
        UsernamePasswordToken token = new UsernamePasswordToken(username,password,StringUtils.isNotBlank(remeber_me));

        Subject subject = SecurityUtils.getSubject();

        try{
            subject.login(token);
            //30分钟失效
            subject.getSession().setTimeout(30* 60 * 1000);
            return RestResponseBo.ok();
        }catch (UnknownAccountException e ){
            return RestResponseBo.fail(e.getMessage());
        }catch (IncorrectCredentialsException e){
            return RestResponseBo.fail(e.getMessage());
        }catch (LockedAccountException e) {
            return RestResponseBo.fail(e.getMessage());
        }catch (AuthenticationException e){
            return RestResponseBo.fail("认证失败！");
        }
    }




}
