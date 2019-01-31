package com.lwx.mystory.controller.admin;

import com.lwx.mystory.constant.WebConstant;
import com.lwx.mystory.controller.BaseController;
import com.lwx.mystory.exception.TipException;
import com.lwx.mystory.model.bo.RestResponseBo;
import com.lwx.mystory.model.dto.Statistics;
import com.lwx.mystory.model.dto.Types;
import com.lwx.mystory.model.entity.Comment;
import com.lwx.mystory.model.entity.Content;
import com.lwx.mystory.model.entity.User;
import com.lwx.mystory.service.IUserService;
import com.lwx.mystory.service.SiteService;
import com.lwx.mystory.utils.TaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 9:10 2019/1/4
 **/
//这里需要注释说明是admin控制层，不然会和前台index报冲突导致
@Controller("adminIndexController")
@RequestMapping("/admin")
public class IndexController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private SiteService siteService;

    @Autowired
    private IUserService userService;

    /**
     * 首页展示
     * @param request
     * @return
     */
    @GetMapping(value = {"/","index"})
    public String index(HttpServletRequest request){
        //获取最近的10条评论和文章
        List<Comment> comments =  siteService.getRecentComments(10);
        List<Content> contents = siteService.getContents(Types.RECENT_ARTICLE,10);
        Statistics statistics = new Statistics();
        request.setAttribute("comments",comments);
        request.setAttribute("articles",contents);
        request.setAttribute("statistics",statistics);
        return "admin/index";
    }

    /**
     * 个人设置
     * @param request
     * @return
     */
    @GetMapping("/profile")
    public String profile(HttpServletRequest request){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("user",user);
        return "admin/profile";
    }

    /**
     * 保存个人信息
     * @param screenName
     * @param email
     * @return
     */
    @PostMapping("/profile")
    @ResponseBody
    public RestResponseBo saveProfile(@RequestParam String screenName,
                                      @RequestParam String email){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(StringUtils.isNotBlank(screenName) && StringUtils.isNotBlank(email)){
            User temp =  new User();
            temp.setId(user.getId());
            temp.setEmail(email);
            temp.setScreen_name(screenName);
            userService.updateUser(temp);
            //更新session中的数据
            user.setScreen_name(screenName);
            user.setEmail(email);
            SecurityUtils.getSubject().getSession().setAttribute(WebConstant.LOGIN_SESSION_KEY,user);
            return RestResponseBo.ok();
        }
        return  RestResponseBo.fail("邮箱和昵称不可以为空！");
    }

    @PostMapping("/password")
    @ResponseBody
    public RestResponseBo modifyPassword(@RequestParam String oldPassword,
                                         @RequestParam String password,
                                         HttpSession session){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(StringUtils.isBlank(oldPassword) || StringUtils.isBlank(password)){
            return RestResponseBo.fail("请确认信息输入完整");
        }
        if(!user.getPassword().equals(TaleUtils.MD5encode(user.getUsername()+oldPassword))){
            return RestResponseBo.fail("旧密码错误");
        }
        if(password.length() < 6 || password.length() > 14){
            return RestResponseBo.fail("请输入6-14位密码");
        }

        try{
            User temp =new User();
            temp.setId(user.getId());
            String pwd = TaleUtils.MD5encode(user.getUsername() + password);
            temp.setPassword(pwd);
            userService.updateUser(temp);

            //更新session中的数据
            User original = (User) session.getAttribute(WebConstant.LOGIN_SESSION_KEY);
            original.setPassword(pwd);
            session.setAttribute(WebConstant.LOGIN_SESSION_KEY,original);
            return RestResponseBo.ok();
        }catch (Exception e){
            String msg = "密码修改失败";
            if(e instanceof TipException){
                msg = e.getMessage();
            }else {
                logger.error(msg,e);
            }
            return RestResponseBo.fail(msg);
        }
    }

}
