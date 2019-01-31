package com.lwx.mystory.controller.admin;

import com.github.pagehelper.PageInfo;
import com.lwx.mystory.constant.WebConstant;
import com.lwx.mystory.controller.BaseController;
import com.lwx.mystory.model.bo.RestResponseBo;
import com.lwx.mystory.model.dto.Types;
import com.lwx.mystory.model.entity.Content;
import com.lwx.mystory.model.entity.User;
import com.lwx.mystory.service.IContentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;

/**
 * @Descripiton: 文章管理和页面管理类似：只不过是特殊的文章，只有admin才可以管理
 * @Author:linwx
 * @Date；Created in 21:16 2019/1/13
 **/
@Controller
@RequestMapping("admin/page")
public class PageController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

    @Autowired
    private IContentService contentService;

    @GetMapping("")
    public String index(HttpServletRequest request){
        PageInfo<Content> contentPageInfo = contentService.getArticlesWithPage(1,WebConstant.MAX_POSTS);
        request.setAttribute("articles",contentPageInfo);
        return "admin/page_list";
    }

    @GetMapping("new")
    public String newPage(){
        return "admin/page_edit";
    }

    @GetMapping("/{cid}")
    public String editPage(@PathVariable Integer cid ,HttpServletRequest request){
        Content content = contentService.getContentById(cid);
        request.setAttribute("contents",content);
        return "admin/page_edit";
    }

    @PostMapping(value = "publish")
    @ResponseBody
    @RequiresRoles("admin")
    public RestResponseBo publishPage(@RequestParam String title,
                                      @RequestParam String content,
                                      @RequestParam String status,
                                      @RequestParam String slug,
                                      @RequestParam(required = false) Integer allowComment,
                                      @RequestParam(required = false) Integer allowPing){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Content newContent = new Content();
        newContent.setTitle(title);
        newContent.setContent(content);
        newContent.setStatus(status);
        newContent.setSlug(slug);
        newContent.setType(Types.PAGE);
        //是否允许评论
        if(null != allowComment){
            newContent.setAllowComment(allowComment);
        }
        //是否允许ping
        if(null != allowPing){
            newContent.setAllowPing(allowPing);
        }
        newContent.setAuthorId(user.getId());
        String result = contentService.saveContent(newContent);
        if(!WebConstant.SUCCESS_RESULT.equals(result)){
            return RestResponseBo.fail(result);
        }
        return RestResponseBo.ok();
    }

    @PostMapping(value = "modify")
    @ResponseBody
    @RequiresRoles("admin")
    public RestResponseBo modifyArticle(@RequestParam Integer cid,
                                        @RequestParam String content,
                                        @RequestParam String title,
                                        @RequestParam String status,
                                        @RequestParam String slug,
                                        @RequestParam(required = false) Integer allowComment,
                                        @RequestParam(required = false) Integer allowPing){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Content contents = new Content();
        contents.setCid(cid);
        contents.setTitle(title);
        contents.setContent(content);
        contents.setStatus(status);
        contents.setSlug(slug);
        contents.setType(Types.PAGE);
        if (null != allowComment) {
            contents.setAllowComment(allowComment);
        }
        if (null != allowPing) {
            contents.setAllowPing(allowPing);
        }
        contents.setAuthorId(user.getId());
        // 更新文章
        boolean flag = contentService.updateContent(contents);
        if (!flag) {
            return RestResponseBo.fail("FAIL");
        }
        return RestResponseBo.ok();
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    @RequiresRoles("admin")
    public RestResponseBo delete(@RequestParam int cid){
        String result = contentService.deleteContentById(cid);
        if(!WebConstant.SUCCESS_RESULT.equals(result)){
            return RestResponseBo.fail(result);
        }
        return RestResponseBo.ok();
    }

}
