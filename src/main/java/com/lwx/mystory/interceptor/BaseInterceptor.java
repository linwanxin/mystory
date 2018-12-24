package com.lwx.mystory.interceptor;

import com.lwx.mystory.extension.AdminCommons;
import com.lwx.mystory.extension.Commons;
import com.lwx.mystory.model.entity.Option;
import com.lwx.mystory.service.IOptionService;
import com.lwx.mystory.utils.IPKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 19:50 2018/12/6
 **/
@Component
public class BaseInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);

    private static final String USER_AGENT = "user-agent";

    @Autowired
    private IOptionService optionService;

    @Autowired
    private Commons commons;

    @Autowired
    private AdminCommons adminCommons;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        //logger.info("UserAgent: {}",request.getHeader(USER_AGENT));
        logger.info("用户访问地址：{}，来路地址：{}", uri, IPKit.getIPAddrByRequest(request));

        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        Option ov = optionService.getOptionByName("site_record");
        request.setAttribute("commons", commons);
        request.setAttribute("option", ov);
        request.setAttribute("adminCommons", adminCommons);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
