package com.lwx.mystory.controller;

import com.github.pagehelper.PageInfo;
import com.lwx.mystory.constant.WebConstant;
import com.lwx.mystory.model.bo.RestResponseBo;
import com.lwx.mystory.model.dto.Archive;
import com.lwx.mystory.model.dto.Types;
import com.lwx.mystory.model.entity.Comment;
import com.lwx.mystory.model.entity.Content;
import com.lwx.mystory.model.entity.Meta;
import com.lwx.mystory.service.*;
import com.lwx.mystory.utils.IPKit;
import com.lwx.mystory.utils.TaleUtils;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class IndexController extends BaseController {
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
     * 首页分页展示
     * @param request
     * @param limit
     */
    @GetMapping(value = "/")
    public String index(HttpServletRequest request,
                        @RequestParam(value = "limit", defaultValue = "12") int limit) {
        visitCount(request);
        return this.index(request, 1, limit);
    }
    @GetMapping("page/{p}")
    public String index(HttpServletRequest request,
                        @PathVariable int p,
                        @RequestParam(value = "limit", defaultValue = "12") int limit) {
        //测试验证可以等于0
        p = p < 0 || p > WebConstant.MAX_PAGE ? 1 : p;
        PageInfo<Content> articles = contentService.getContentsByPageInfo(p, limit);
        request.setAttribute("articles", articles);
        if (p > 1) {
            this.title(request, "第" + p + "页");
        }
        return this.render("index");
    }

    /**
     * 文章详情页
     * @param request
     * @param cid     文章ID
     * @param coid    评论页码，前台传过来的保证是1,所以不需要分页
     */
    @GetMapping("article/{cid}/{coid}")
    public String getArticle(HttpServletRequest request,
                             @PathVariable String cid,
                             @PathVariable String coid) {
        Content content = contentService.getContentById(Integer.parseInt(cid));
        if (null == content || "draft".equals(content.getStatus())) {
            return this.render_404();
        }
        request.setAttribute("article", content);
        request.setAttribute("is_post", true);
        //获取评论
        getArticleComments(coid, request, content);
        if (!checkHitsFrequency(request, cid)) {
            //更新文章点击量
            updateArticleHit(content.getCid(), content.getHits());
        }
        return this.render("post");
    }

    /**
     * 归档页面
     */
    @GetMapping("archives")
    public String archives(HttpServletRequest request) {
        List<Archive> archiveList = siteService.getArchives();
        request.setAttribute("archives", archiveList);
        return render("archives");
    }

    /**
     * 自定义页面，比如：about页面(slug的作用)
     * pagename：文章slug
     * coid ： 评论页码
     */
    @GetMapping("custom/{pagename}")
    public String custom(@PathVariable String pagename,
                         HttpServletRequest request) {
        return custom(pagename, "1", request);
    }
    @GetMapping("{pagename}/{coid}")
    public String custom(@PathVariable String pagename,
                         @PathVariable String coid,
                         HttpServletRequest request) {
        //根据文章缩略名（slug）来查询文章
        Content content = contentService.getContentBySlug(pagename);
        if (content == null) {
            return this.render_404();
        }
        //文章是否允许评论
        if (content.getAllowComment() == 1) {
            PageInfo<Comment> comments= commentService.getCommentsByContentId(content.getCid(), Integer.parseInt(coid), 6);
            request.setAttribute("comments", comments);
        }
        request.setAttribute("article", content);
        if (!checkHitsFrequency(request, String.valueOf(content.getCid()))) {
            //更新文章的点击量
            updateArticleHit(content.getCid(), content.getHits());
        }
        return this.render("page");
    }

    /**
     * 友情链接
     */
    @GetMapping("links")
    public String links(HttpServletRequest request) {
        List<Meta> links = metaService.getMetasByType(Types.LINK);
        request.setAttribute("links", links);
        return this.render("links");
    }

    /**
     * 文章里的标签页
     * @param name
     * @param limit
     */
    @GetMapping(value = "tag/{name}")
    public String tags(HttpServletRequest request,
                       @PathVariable String name,
                       @RequestParam(value = "limit", defaultValue = "12") int limit) {
        return this.tags(request, name, 1, limit);
    }
    @GetMapping(value = "tag/{name}/{page}")
    public String tags(HttpServletRequest request,
                       @PathVariable String name,
                       @PathVariable int page,
                       @RequestParam(value = "limit", defaultValue = "12") int limit) {
        page = page < 0 || page > WebConstant.MAX_PAGE ? 1 : page;
        //对于空格的特殊处理
        name = name.replaceAll("\\+", " ");
        Meta meta = metaService.getMetaByTypeAndName(Types.TAG, name);
        if (null == meta) {
            return this.render_404();
        }

        PageInfo<Content> articles = contentService.getTagArticles(meta.getMid(), page, limit);
        request.setAttribute("articles", articles);
        request.setAttribute("type", "标签");
        request.setAttribute("keyword", name);
        return this.render("page-category");
    }

    /**
     * 在首页文章显示的分类,如：默认之类等
     *
     */
    @GetMapping(value = "category/{keyword}")
    public String categories(HttpServletRequest request,
                             @PathVariable String keyword,
                             @RequestParam(value = "limit", defaultValue = "12") int limit) {
        return this.categories(request, keyword, 1, limit);
    }
    @GetMapping("category/{keyword}/{page}")
    public String categories(HttpServletRequest request,
                             @PathVariable String keyword,
                             @PathVariable int page,
                             @RequestParam(value = "limit", defaultValue = "12") int limit) {
        page = page < 0 || page > WebConstant.MAX_PAGE ? 1 : page;
        Meta meta = metaService.getMetaByTypeAndName(Types.CATEGORY, keyword);
        if (null == meta) {
            return this.render_404();
        }
        PageInfo<Content> contentPageInfo = contentService.getTagArticles(meta.getMid(), page, limit);

        request.setAttribute("articles", contentPageInfo);
        request.setAttribute("meta", meta);
        request.setAttribute("type", "分类");
        request.setAttribute("keyword", keyword);

        return this.render("page-category");
    }

    /**
     * 首页中的搜索框：是根据文章的标题搜索文章！
     * @param request
     * @param keyword
     * @param limit
     */
    @GetMapping("search/{keyword}")
    public String search(HttpServletRequest request,
                         @PathVariable String keyword,
                         @RequestParam(value = "limit", defaultValue = "12") int limit) {
        return this.search(request, keyword, 1, limit);
    }
    public String search(HttpServletRequest request,
                         @PathVariable String keyword,
                         @PathVariable int page,
                         @RequestParam(value = "limit", defaultValue = "12") int limit) {
        page = page < 0 || page > WebConstant.MAX_PAGE ? 1 : page;
        PageInfo<Content> articles = contentService.getContentsByTitle(keyword, page, limit);
        request.setAttribute("articles", articles);
        request.setAttribute("type", "搜索");
        request.setAttribute("keyword", keyword);
        return this.render("page-category");
    }

    /**
     * 首页Tag页面
     */
    @GetMapping("about/searchPage")
    public String searchTags(HttpServletRequest request) {
        List<Meta> tags = metaService.getMetas(Types.TAG, WebConstant.MAX_TAGS);
        request.setAttribute("tagList", tags);
        return this.render("search");
    }

    /**
     * 灵魂画师
     */
    @GetMapping("soulPainter.html")
    public String soulPainter() {
        return "themes/painter/soulpainter";
    }

    /**
     * 记录访客数量
     */
    public synchronized void visitCount(HttpServletRequest request) {
        String val = IPKit.getIPAddrByRequest(request);
        //缓存中是否存在
        Integer count = cache.hget(Types.VISIT_COUNT, val);
        if (count != null && count > 0) {
            //暂时什么也不做！
        } else {
            //存入缓存并设置超时时间
            cache.hset(Types.VISIT_COUNT, val, 1, WebConstant.VISIT_COUNT_TIME);
            //visit表仅仅是用来记录访问网站被不同IP的访问次数
            Integer times = visitService.getCountById(1).getCount();
            //入库
            visitService.updateCountById(times + 1);
        }
    }

    /**
     * 更新文章的点击量
     */
    private void updateArticleHit(Integer cid, Integer chits) {
        if (chits == 0 || chits == null) {
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
    private boolean checkHitsFrequency(HttpServletRequest request, String cid) {
        String val = IPKit.getIPAddrByRequest(request) + ":" + cid;
        Integer count = cache.hget(Types.HITS_FREQUENCY, val);
        if (count != null && count > 0) {
            return true;
        }
        //如果未访问过就设置在缓存中
        cache.hset(Types.HITS_FREQUENCY, val, 1, WebConstant.HITS_LIMIT_TIME);
        return false;
    }

    /**
     * 文章详情页的评论分页
     * @param coid   评论页码
     * @param request
     * @param content
     */
    private void getArticleComments(String coid, HttpServletRequest request, Content content) {
        //允许评论
        if (content.getAllowComment() == 1) {
            //每页6条评论
            PageInfo<Comment> comments = commentService.getCommentsByContentId(content.getCid(), Integer.parseInt(coid), 6);
            request.setAttribute("comments", comments);
        }
    }

    /**
     * 评论
     */
    @PostMapping("comment")
    @ResponseBody
    public RestResponseBo comment(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam Integer cid,
                                  @RequestParam Integer coid,
                                  @RequestParam String author,
                                  @RequestParam String mail,
                                  @RequestParam String url,
                                  @RequestParam String text){
        //一些列过滤条件（其实前台做了些控制，但是可能是防止模拟提交做的限制！）
        if(null == cid || StringUtils.isBlank(text)){
            return RestResponseBo.fail("请输入完整后评论！");
        }
        if(StringUtils.isNotBlank(author) && author.length() > 20){
            return RestResponseBo.fail("姓名过长！");
        }
        if(StringUtils.isNotBlank(mail) && !TaleUtils.isEmail(mail)){
            return RestResponseBo.fail("请输入正确的邮箱格式！");
        }
        if(text.length() > 200){
            return RestResponseBo.fail("请输入200个字符以内的评论！");
        }

        String ip = IPKit.getIPAddrByRequest(request);
        String val = ip + ":" + cid;
        Integer count = cache.hget(Types.COMMENTS_FREQUENCY,val);
        if(null != count && count > 0 ){
            return RestResponseBo.fail("您发表评论太快了，请过会再试！");
        }

        author = TaleUtils.cleanXSS(author);
        text = TaleUtils.cleanXSS(text);

        author = EmojiParser.parseToAliases(author);
        text = EmojiParser.parseToAliases(text);

        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setCid(cid);
        comment.setIp(ip);
        comment.setUrl(url);
        comment.setContent(text);
        comment.setMail(mail);
        comment.setParent(null == coid ? 0: coid);
        try{
            String result = commentService.insertComment(comment);
            // 此处增加cookie是为了不让用户再次输入评论头部
            if(StringUtils.isNotBlank(author)) {
                setCookie("tale_remember_author", URLEncoder.encode(author, "UTF-8"), 7 * 24 * 60 * 60, response);
            }
            if(StringUtils.isNotBlank(mail)) {
                setCookie("tale_remember_mail", URLEncoder.encode(mail, "UTF-8"), 7 * 24 * 60 * 60, response);
            }
            if (StringUtils.isNotBlank(url)) {
                setCookie("tale_remember_url", URLEncoder.encode(url, "UTF-8"), 7 * 24 * 60 * 60, response);
            }
            //设置对每个文章1分钟评论1次
            cache.hset(Types.COMMENTS_FREQUENCY,val,1,60);
            if(!WebConstant.SUCCESS_RESULT.equals(result)){
                return RestResponseBo.fail(result);
            }

            return RestResponseBo.ok();
        }catch (Exception e){
            String msg = "评论发布失败";
            logger.error(msg,e);
            return RestResponseBo.fail(msg);
        }
    }

    /**
     * 设置Cookie
     */
    private void setCookie(String name,String value,int maxAge,HttpServletResponse response){
        Cookie cookie = new Cookie(name,value);
        cookie.setMaxAge(maxAge);
        cookie.setSecure(false);
        response.addCookie(cookie);
    }

}
