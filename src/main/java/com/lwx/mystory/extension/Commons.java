package com.lwx.mystory.extension;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwx.mystory.model.dto.Types;
import com.lwx.mystory.model.entity.Comment;
import com.lwx.mystory.model.entity.Content;
import com.lwx.mystory.model.entity.Option;
import com.lwx.mystory.service.ICommentService;
import com.lwx.mystory.service.IContentService;
import com.lwx.mystory.service.IOptionService;
import com.lwx.mystory.service.IVisitService;
import com.lwx.mystory.utils.DateKit;
import com.lwx.mystory.utils.TaleUtils;
import com.lwx.mystory.utils.UUID;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Descripiton:公共主题函数
 * @Author:linwx
 * @Date；Created in 15:57 2018/12/6
 **/
@Component
public class Commons {
    public static String THEME  = "themes/front";

    private static IOptionService optionService;

    @Autowired
    private IContentService contentService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IVisitService visitService;


    @Autowired
    public void setOptionService(IOptionService optionService){
        Commons.optionService = optionService;
    }

    /**
     * 网站配置项
     * @param key
     * @return
     */
    public static String site_option(String key){
        return site_option(key,"");
    }
    public static String site_option(String key,String defaultValue){
        if(StringUtils.isBlank(key)){
            return "";
        }
        //从数据库读取
        Option option = optionService.getOptionByName(key);
        String str = option.getValue();
        if(StringUtils.isNotBlank(str)){
            return str;
        }else{
            return defaultValue;
        }
    }

    /**
     * 返回主题URL
     *
     * @return
     */
    public static String theme_url() {
        return site_url(Commons.THEME);
    }

    /**
     * 显示文章的缩略图：封面背景图
     * @param content
     * @return
     */
    public static String show_thumb(Content content){
        int cid = content.getCid();
        int size = cid % 40;
        size = size == 0 ? 10 :size;

        return "/user/img/picture/" + size + ".jpg";
    }
    /**
     * 获取随机数
     * @param max
     * @param str
     * @return
     */
    public static String random(int max, String str) {
        return UUID.random(1, max) + str;
    }

    /**
     * 返回文章链接地址
     */
    public static String permalink(Content content){
        return permalink(content.getCid(),content.getSlug());
    }
    public static String permalink(Integer cid,String slug){
        return site_url("/article/"+ (StringUtils.isNotBlank(slug)? slug : cid.toString()));
    }

    /**
     * 返回网站链接下的全址
     * @param sub
     * @return
     */
    public static String site_url(String sub){
        return site_option("site_url") + sub;
    }

    /**
     * 首页截取文章摘要:取文章前70个长度
     * @param content
     * @param len
     * @return
     */
    public static String intro(Content content,int len){
        String value = content.getContent();
        int pos = value.indexOf("<!--more-->");
        if(pos != -1){
            String html = value.substring(0,pos);
            String text = TaleUtils.htmlToText(TaleUtils.mdToHtml(html));
            if(text.length() > len){
                return text.substring(0,len);
            }
            return text;
        }else {
            String text = TaleUtils.htmlToText(TaleUtils.mdToHtml(value));
            if(text.length() > len){
                return text.substring(0,len);
            }
            return text;
        }
    }
    /**
     * <p>
     * 这种格式的字符转换为emoji表情
     */
    public static String emoji(String value){
        return EmojiParser.parseToUnicode(value);
    }

    private static final String[] ICONS = {"bg-ico-book", "bg-ico-game", "bg-ico-note", "bg-ico-chat", "bg-ico-code", "bg-ico-image", "bg-ico-web", "bg-ico-link", "bg-ico-design", "bg-ico-lock"};
    /**
     * 显示文章图标
     */
    public static String show_icon(int cid){
        return ICONS[cid % ICONS.length];
    }

    public static String show_categories(String categories) throws UnsupportedEncodingException {
        if(StringUtils.isNotBlank(categories)){
            String [] arr = categories.split(",");
            StringBuffer sbuf = new StringBuffer();
            for(String c : arr){
                sbuf.append("<a href=\"/category/" + URLEncoder.encode(c,"UTF-8").toString() + "\">" + c + "</a>");
            }
            return sbuf.toString();
        }
        return show_categories("默认分类");
    }

    public Map<String,String> social(){
        final String prefix = "social_";
        Map<String,String> map = new HashMap<>();
        map.put("weibo",optionService.getOptionByName(prefix+ "weibo").getValue());
        map.put("zhihu", optionService.getOptionByName(prefix + "zhihu").getValue());
        map.put("github", optionService.getOptionByName(prefix + "github").getValue());
        map.put("mayun", optionService.getOptionByName(prefix + "mayun").getValue());
        return map;
    }

    /**
     * 网站标题
     *
     * @return
     */
    public static String site_title() {
        return site_option("site_title");
    }

    /**
     * 获取最近8篇文章
     * @return
     */
    public List<Content> getContents(){
        PageInfo<Content> pageInfo = contentService.getContentsByCondititions(Types.ARTICLE,1,8);
        return pageInfo.getList();
    }

    public List<Comment> getComments(){
        PageHelper.startPage(1,8);
        List<Comment> commentList = commentService.selectCommentsByAuthorId(1);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);
        return pageInfo.getList();
    }

    public Integer getVisitCount(){
        return visitService.getCountById(1).getCount();
    }

    /**
     * post页面:格式化unix时间戳为日期
     */
    public static String fmtdate(Integer unixTime){
        return fmtdate(unixTime,"yyyy-MM-dd");
    }
    public static String fmtdate(Integer unixTime,String patten){
        if(null !=unixTime && StringUtils.isNotBlank(patten)){
            return DateKit.formatDateByUnixTime(unixTime,patten);
        }
        return "";
    }

    /**
     *文章详情页显示标签
     */
    public static String show_tags(String tags) throws UnsupportedEncodingException {
        if(StringUtils.isNotBlank(tags)){
            String [] arr = tags.split(",");
            StringBuffer sbuf = new StringBuffer();
            for(String s : arr){
                sbuf.append("<a href=\"/tag/"+URLEncoder.encode(s,"UTF-8") + "\">" +s + "</a>");
            }
            return sbuf.toString();
        }
        return "";
    }
    /**
     *post页面：转换markdown为html
     */
    public static String article(String value){
        if(StringUtils.isNotBlank(value)){
            value = value.replace("<!--more-->","\r\n");
            return TaleUtils.mdToHtml(value);
        }
        return "";
    }
}
