package com.lwx.mystory.controller;

import com.lwx.mystory.service.IVisitService;
import com.lwx.mystory.utils.IPKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends  BaseController{
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IVisitService visitService;
    


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

        Integer count = cache
    }
}
