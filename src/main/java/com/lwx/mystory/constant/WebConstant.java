package com.lwx.mystory.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:linwx
 * @Date；Created in 9:29 2018/12/2
 **/
public class WebConstant {
    public static Map<String, String> initConfig = new HashMap<>();
    public static String LOGIN_SESSION_KEY = "login_user";

    //最大获取文章条数
    public static final int MAX_POSTS = 9999;
    //最大页码
    public static final int MAX_PAGE = 100;
    //文章最多可以输入的字数
    public static final int MAX_TEXT_COUNT = 200000;
    //最大标签数
    public static final int MAX_TAGS = 9999;

    //访问次数记录，30分钟
    public static Integer VISIT_COUNT_TIME = 1800;

    //文章的访问点击频率
    public static Integer HITS_LIMIT_TIME = 900;

    public static String STATUS_0 = "0";

    /**
     * 成功返回
     */
    public static String SUCCESS_RESULT = "SUCCESS";
}
