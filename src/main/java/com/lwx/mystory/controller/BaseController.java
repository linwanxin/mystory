package com.lwx.mystory.controller;

import com.lwx.mystory.model.entity.Users;
import com.lwx.mystory.utils.MapCache;
import com.lwx.mystory.utils.TaleUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制层的基类
 */
public class BaseController {

    public static String THEME = "themes/front";

    protected MapCache cache = MapCache.single();

    public String render(String viewName) {
        return THEME + "/" + viewName;
    }

    /**
     * 就在在index中用到，貌似没用！
     * @param request
     * @param title
     * @return
     */
    public BaseController title(HttpServletRequest request, String title) {
        request.setAttribute("title", title);
        return this;
    }

    public BaseController keywords(HttpServletRequest request, String keywords) {
        request.setAttribute("keywords", keywords);
        return this;
    }

    public Users user(HttpServletRequest request) {
        return TaleUtils.getLoginUser(request);
    }

    public Integer getUserid(HttpServletRequest request) {
        return this.user(request).getId();
    }

    public String render_404() {
        return "comm/error_404";
    }

}
