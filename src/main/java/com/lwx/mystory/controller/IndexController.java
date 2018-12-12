package com.lwx.mystory.controller;

import com.github.pagehelper.PageInfo;
import com.lwx.mystory.constant.WebConstant;
import com.lwx.mystory.model.dto.Archive;
import com.lwx.mystory.model.dto.Types;
import com.lwx.mystory.model.entity.Comment;
import com.lwx.mystory.model.entity.Content;
import com.lwx.mystory.model.entity.Meta;
import com.lwx.mystory.service.*;
import com.lwx.mystory.utils.IPKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController extends  BaseController{
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IVisitService visitService;
    @Autowired
    private IContentService contentService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IMetaService metaService;
    @Autowired
    private SiteService siteService;

    /**
     * 首页
     * @param request
     * @param limit
     * @return
     */
    @GetMapping(value = "/")
    public String index(HttpServletRequest request,
                        @RequestParam(value = "limit",defaultValue = "12") int limit){
        visitCount(request);
        return this.index(request,1,limit);
    }

    /**
     * 记录访客数量
     * @param request
     */
    public synchronized void visitCount(HttpServletRequest request){
        String val = IPKit.getIPAddrByRequest(request);
        //visit表仅仅是用来记录访问网站被不同IP的访问次数
        Integer times = visitService.getCountById(1).getCount();
        //缓存中是否存在
        Integer count  = cache.hget(Types.VISIT_COUNT,val);
        if(count != null && count >0){
               //暂时什么也不做！
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

    /**
     * 文章详情页
     * @param request
     * @param cid 文章ID
     * @param coid 评论页码
     * @return
     */
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
        //获取评论
        completArticle(coid,request,content);
        if(!checkHitsFrequency(request,cid)){
            //更新文章点击量
            updateArticleHit(content.getCid(),content.getHits());
        }
        return this.render("post");
    }

    /**
     * 文章里的标签;分页处理
     * @param request
     * @param name
     * @param limit
     * @return
     */
    @GetMapping(value = "tag/{name}")
    public String tags(HttpServletRequest request,
                       @PathVariable String name,
                       @RequestParam(value = "limit", defaultValue = "12") int limit){
        return this.tags(request,name,1,limit);
    }

    @GetMapping(value = "tag/{name}/{page}")
    public String tags(HttpServletRequest request,
                       @PathVariable String name,
                       @PathVariable int page,
                       @RequestParam(value = "limit", defaultValue = "12") int limit){
        page = page < 0 || page > WebConstant.MAX_PAGE ? 1 : page;
        //对于空格的特殊处理
        name = name.replaceAll("\\+"," ");
        Meta meta = metaService.getMeta(Types.TAG,name);
        if(null == meta){
            return this.render_404();
        }

        PageInfo<Content> contentPageInfo = contentService.getTagArticles(meta.getMid(),page,limit);
        request.setAttribute("articles",contentPageInfo);
        request.setAttribute("type","标签");
        request.setAttribute("keyword",name);

        return this.render("page-category");
    }

    /**
     * 归档页面
     */
    @GetMapping("archives")
    public String archives(HttpServletRequest request){
        List<Archive> archiveList = siteService.getArchives();
        request.setAttribute("archives",archiveList);
        return render("archives");
    }

    /**
     * 自定义页面，比如：about页面(原来slug的作用是这个)
     */
    @GetMapping("custom/{pagename}")
    public String page(@PathVariable String pagename,
                       HttpServletRequest request){
        return page(pagename,"1",request);
    }
    @GetMapping("{pagename}/{coid}")
    public String page(@PathVariable String pagename,
                       @PathVariable String coid,
                       HttpServletRequest request){
        //根绝缩略名来查询文章
        Content content  = contentService.getContentBySlug(pagename);
        if(content == null){
            return this.render_404();
        }
        //文章是否允许评论
        if(content.getAllowComment() == 1){
            PageInfo<Comment> commentPageInfo = commentService.getCommentsByContentId(content.getCid(),Integer.parseInt(coid),6);
            request.setAttribute("comments",commentPageInfo);
        }
        request.setAttribute("article",content);
        if(!checkHitsFrequency(request,String.valueOf(content.getCid()))){
            //更新文章的点击量
            updateArticleHit(content.getCid(),content.getHits());
        }
        return this.render("page");
    }




    /**
     * @param cid
     * @param chits
     */
    private void updateArticleHit(Integer cid,Integer chits){
        if(chits == 0 || chits == null){
            chits = 0;
        }
        chits += 1;
        Content tempContent = new Content();
        tempContent.setCid(cid);
        tempContent.setHits(chits);
        contentService.updateContent(tempContent);
    }

    /**
     * 检查文章点击频率
     * @param request
     * @param cid
     * @return
     */
    private boolean checkHitsFrequency(HttpServletRequest request,String cid){
        String val = IPKit.getIPAddrByRequest(request)+ ":" + cid ;
        Integer count = cache.hget(Types.HITS_FREQUENCY,val);
        if(count != null && count > 0 ){
            return true;
        }
        //如果未访问过就设置在缓存中
        cache.hset(Types.HITS_FREQUENCY,val,1,WebConstant.HITS_LIMIT_TIME);
        return false;
    }
    /**
     * 文章详情页的评论分页
     * @param coid commentId
     * @param request
     * @param content
     */
    private void completArticle(String coid,HttpServletRequest request,Content content){
        //允许评论
        if(content.getAllowComment() == 1){
            //每页6条评论
            PageInfo<Comment> comments  = commentService.getCommentsByContentId(content.getCid(),Integer.parseInt(coid),6);
            request.setAttribute("comments",comments);
        }
    }

}
