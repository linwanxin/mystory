package com.lwx.mystory.controller.admin;

import com.lwx.mystory.controller.BaseController;
import com.lwx.mystory.model.dto.Statistics;
import com.lwx.mystory.model.dto.Types;
import com.lwx.mystory.model.entity.Comment;
import com.lwx.mystory.model.entity.Content;
import com.lwx.mystory.service.IUserService;
import com.lwx.mystory.service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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


    @GetMapping(value = {"/","index"})
    public String index(HttpServletRequest request){

        List<Comment> comments =  siteService.getRecentComments(10);
        List<Content> contents = siteService.getContents(Types.RECENT_ARTICLE,10);
        Statistics statistics = new Statistics();
        request.setAttribute("comments",comments);
        request.setAttribute("articles",contents);
        request.setAttribute("statistics",statistics);
        return "admin/index";
    }
}
