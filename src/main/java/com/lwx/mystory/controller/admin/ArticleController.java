package com.lwx.mystory.controller.admin;

import com.github.pagehelper.PageInfo;
import com.lwx.mystory.constant.WebConstant;
import com.lwx.mystory.model.bo.RestResponseBo;
import com.lwx.mystory.model.dto.Types;
import com.lwx.mystory.model.entity.Content;
import com.lwx.mystory.model.entity.Meta;
import com.lwx.mystory.model.entity.User;
import com.lwx.mystory.service.IContentService;
import com.lwx.mystory.service.IMetaService;
import com.lwx.mystory.utils.DateKit;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 9:58 2019/1/6
 **/
@Controller
@RequestMapping("/admin/article")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private IMetaService metaService;
    @Autowired
    private IContentService contentService;

    /**
     * 文章管理
     * @param page
     * @param limit
     * @param request
     * @return
     */
    @GetMapping("")
    @ApiOperation(value = "后台文章管理列表", notes = "后台文章管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "起始页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页条数", required = true, dataType = "Integer")
    })
    public String managerArticle(@RequestParam(value = "page",defaultValue = "1") int page,
                                 @RequestParam(value = "limit",defaultValue = "10") int limit,
                                 HttpServletRequest request){
        //如果数据为null，则在解析模板时候会报错！
        PageInfo<Content> contentPageInfo = contentService.getContentsByUserId(Types.ARTICLE,page,limit);
        request.setAttribute("articles",contentPageInfo);
        return "admin/article_list";
    }
    /**
     * 发布文章
     * @param request
     * @return
     */
    @GetMapping(value = "/publish")
    public String publishArticle(HttpServletRequest request){
        List<Meta> categories = metaService.getMetasByType(Types.CATEGORY);
        request.setAttribute("categories",categories);
        return "admin/article_edit";
    }

    @PostMapping("/publish")
    @ResponseBody
    public RestResponseBo saveArticle(Content content){
        //登陆人
        User user  = (User) SecurityUtils.getSubject().getPrincipal();
        content.setAuthorId(user.getId());

        content.setType(Types.ARTICLE);
        if(StringUtils.isBlank(content.getCategories())){
            content.setCategories("默认分类");
        }
        String result = contentService.saveContent(content);
        if(!result.equals(WebConstant.SUCCESS_RESULT)){
            return RestResponseBo.fail(result);
        }
        return RestResponseBo.ok();
    }


    /**
     * 编辑文章
     * @param cid
     * @param request
     * @return
     */
    @GetMapping("/{cid}")
    @ApiOperation(value = "编辑文章",notes = "后台编辑文章")
    @ApiImplicitParam(name = "cid",value = "文章id",required = true,dataType = "String",paramType = "path")
    public String editArticle(@PathVariable String cid,HttpServletRequest request){
        Content content = contentService.getContentById(Integer.parseInt(cid));
        request.setAttribute("contents",content);
        List<Meta>  categories = metaService.getMetasByType(Types.CATEGORY);
        request.setAttribute("categories",categories);
        request.setAttribute("active","article");
        return "admin/article_edit";
    }

    /**
     * 修改文章
     * @param content
     * @return
     */
    @PostMapping("/modify")
    @ResponseBody
    public RestResponseBo modifyArticle(Content content){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        //原文章的作者
        String authorId = contentService.getContentById(content.getCid()).getAuthorId().toString();
        //非本人禁止修改
        if(!authorId.equals(user.getId().toString())){
            return RestResponseBo.fail("抱歉，您无此权限！");
        }
        int time = DateKit.getCurrentUnixTime();
        content.setModified(time);
        content.setAuthorId(user.getId());
        content.setType(Types.ARTICLE);
        String result = "";
        boolean flag = contentService.updateContent(content);
        //修改文章的标签关系表
        contentService.updateRelationShips(content.getCid(),content.getTags(),content.getCategories());
        if(flag){
            return RestResponseBo.ok();
        }else{
            result = "文章更新失败！";
            return RestResponseBo.fail(result);
        }
    }

    /***
     * 删除文章
     * @param cid
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public RestResponseBo delete(@RequestParam int cid){
        String result = contentService.deleteContentById(cid);
        if(!WebConstant.SUCCESS_RESULT.equals(result)){
            return RestResponseBo.fail(result);
        }
        return RestResponseBo.ok();
    }
}
