package com.lwx.mystory.utils;

import com.lwx.mystory.constant.WebConstant;
import com.lwx.mystory.extension.Commons;
import com.lwx.mystory.model.entity.Users;
import org.apache.commons.lang3.StringUtils;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * @Descripiton: tale工具类
 * @Author:linwx
 * @Date；Created in 23:28 2018/12/6
 **/
public class TaleUtils {
    private static final Logger logger = LoggerFactory.getLogger(TaleUtils.class);

    /**
     * markdown解析器
     */
    private static Parser parser = Parser.builder().build();
    /**
     *提取html中的文字
     */
    public static String htmlToText(String html){
        if(StringUtils.isNotBlank(html)){
            return html.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
        }
        return "";
    }
    /**
     * markdown转换为html
     */
    public static String mdToHtml(String markdown){
        if(StringUtils.isBlank(markdown)){
            return "";
        }
        List<Extension> extensions =  Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        String content = renderer.render(document);
        content = Commons.emoji(content);
        return content;
    }


    /**
     * 返回当前登录用户
     *
     * @return
     */
    public static Users getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (null == session) {
            return null;
        }
        return (Users) session.getAttribute(WebConstant.LOGIN_SESSION_KEY);
    }

    /**
     * 获取保存文件的位置,jar所在目录的路径
     *
     * @return
     */
    public static String getUplodFilePath() {
        String path = TaleUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(1, path.length());
        try {
            path = java.net.URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int lastIndex = path.lastIndexOf("/") + 1;
        path = path.substring(0, lastIndex);
        File file = new File("");
        return file.getAbsolutePath() + "/";
    }

}
