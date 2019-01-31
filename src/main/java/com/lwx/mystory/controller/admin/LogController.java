package com.lwx.mystory.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwx.mystory.constant.WebConstant;
import com.lwx.mystory.controller.BaseController;
import com.lwx.mystory.model.bo.RestResponseBo;
import com.lwx.mystory.model.entity.Log;
import com.lwx.mystory.service.ILogService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Descripiton:日志管理
 * @Author:linwx
 * @Date；Created in 17:17 2019/1/30
 **/
@Controller
@RequestMapping("admin/logs")
public class LogController extends BaseController {


    @Autowired
    private ILogService logService;

    /**
     * 首页
     * @param page
     * @param limit
     * @param request
     * @return
     */
    @GetMapping("")
    public String index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int limit,
                        HttpServletRequest request){
        PageHelper.startPage(page,limit);
        List<Log> logs = logService.getVisitLogByAction(WebConstant.VISITOR);
        PageInfo<Log> pageInfo = new PageInfo<>(logs);
        request.setAttribute("logs",pageInfo);
        return "admin/log_list";
    }

    /**
     * 删除日志
     */
    @PostMapping("/delete")
    @ResponseBody
    @RequiresRoles("admin")
    public RestResponseBo deleteLog(@RequestParam int coid){
        String result =  logService.deleteLogById(coid);
        if(WebConstant.SUCCESS_RESULT.equals(result)){
            return RestResponseBo.ok();
        }
        return RestResponseBo.fail(result);
    }

}
