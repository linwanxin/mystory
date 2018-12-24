package com.lwx.mystory.exception;

import com.lwx.mystory.model.bo.RestResponseBo;
import com.lwx.mystory.model.entity.Users;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Descripiton: 全局异常处理类:拦截异常并统一处理
 * @Author:linwx
 * @Date；Created in 15:06 2018/12/6
 **/
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 捕获到相应的异常处理
     *
     * @param
     * @return
     */
    @ExceptionHandler(value = TipException.class)
    public String tipException(Exception e) {
        logger.error("find exception:e={}", e.getMessage());
        e.printStackTrace();
        return "comm/error_500";
    }

    /**
     * 捕获处理无权限的异常
     *
     * @return
     */
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public RestResponseBo handleAuthorizationException() {
        Users user = (Users) SecurityUtils.getSubject().getPrincipal();
        logger.error("用户：" + user.getUsername() + "进行了一次无权限的操作！");
        return RestResponseBo.fail("Sorry!您无此权限！");
    }
}
