package com.lwx.mystory.controller;

import com.github.pagehelper.PageInfo;
import com.lwx.mystory.constant.WebConstant;
import com.lwx.mystory.model.dto.Types;
import com.lwx.mystory.model.entity.Content;
import com.lwx.mystory.service.IContentService;
import com.lwx.mystory.service.IVisitService;
import com.lwx.mystory.utils.IPKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends  BaseController{
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IVisitService visitService;
    @Autowired
    private IContentService contentService;


    @GetMapping(value = "/")
    public String index(HttpServletRequest request,
                        @RequestParam(value = "limit",defaultValue = "12") int limit){
        visitCount(request);
        return this.index(request,1,limit);
    }


    public synchronized void visitCount(HttpServletRequest request){
        String val = IPKit.getIPAddrByRequest(request);
        //id为什么是1？
        Integer times = visitService.getCountById(1).getCount();
        //缓存中是否存在
        Integer count  = cache.hget(Types.VISIT_COUNT,val);
        if(count != null && count >0){

        }else{
            //存入缓存并设置超时时间
            cache.hset(Types.VISIT_COUNT,val,1,WebConstant.VISIT_COUNT_TIME);
            //入库
            visitService.updateCountById(times + 1);
        }
    }

    /**
     * 博客首页分页
     * @param request
     * @param p
     * @param limit
     * @return
     */
    @GetMapping("page/{p}")
    public String index(HttpServletRequest request,
                        @PathVariable int p,
                        @RequestParam(value = "limit",defaultValue = "12") int limit){
        p = p< 0 || p > WebConstant.MAX_PAGE ? 1 :p;
        PageInfo<Content> articles = contentService.getContentsByPageInfo(p,limit);
        request.setAttribute("articles",articles);
        if(p > 1 ){
            this.title(request,"第" + p + "页");
        }
        return this.render("index");
    }

    @GetMapping("article/{cid}/{coid}")
    public String getArticle(HttpServletRequest request,
                             @PathVariable String cid,
                             @PathVariable String coid){
        Content content = contentService.getContentById(Integer.parseInt(cid));
        if(null == content || "draft".equals(content.getStatus())){
            return this.render_404();
        }
        request.setAttribute("article",content);
        request.setAttribute("is_post",true);
        //
        completArticle(coid,request,content);
    }

    private void completArticle(String coid,HttpServletRequest request,Content content){
        if(content.getAllowComment() == 1){
            PageInfo<Content>
        }
    }


}
