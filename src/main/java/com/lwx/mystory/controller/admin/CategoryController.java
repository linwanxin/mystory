package com.lwx.mystory.controller.admin;

import com.lwx.mystory.constant.WebConstant;
import com.lwx.mystory.controller.BaseController;
import com.lwx.mystory.model.bo.RestResponseBo;
import com.lwx.mystory.model.dto.Types;
import com.lwx.mystory.model.entity.Meta;
import com.lwx.mystory.service.IMetaService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Descripiton: 分类/标签管理
 * @Author:linwx
 * @Date；Created in 9:44 2019/1/31
 **/
@Controller
@RequestMapping("admin/category")
public class CategoryController extends BaseController {
    private  static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private IMetaService metaService;

    @GetMapping("")
    public String index(HttpServletRequest request){
        List<Meta> categories = metaService.getMetas(Types.CATEGORY,WebConstant.MAX_POSTS);
        List<Meta> tags = metaService.getMetas(Types.TAG,WebConstant.MAX_POSTS);
        request.setAttribute("categories",categories);
        request.setAttribute("tags",tags);
        return "admin/category";
    }

    @PostMapping("save")
    @RequiresPermissions("create")
    @ResponseBody
    public RestResponseBo saveCategory(@RequestParam String cname,
                                       @RequestParam Integer mid){
        try{
            metaService.saveMeta(Types.CATEGORY,cname,mid);
        }catch (Exception e){
            String msg = "分类保存失败";
            LOGGER.error(msg,e);
            return RestResponseBo.fail(msg);
        }
        return RestResponseBo.ok();
    }

    //这个删除原逻辑问题很大，需要好好考虑想清楚重写！
//    @PostMapping("delete")
//    @RequiresPermissions("delete")
//    @ResponseBody
//    public RestResponseBo delete(@RequestParam int mid){
//        try{
//            metaService.delMetaById(mid);
//        }catch (Exception e){
//            String msg = "删除失败";
//            LOGGER.error(msg,e);
//            return RestResponseBo.fail(msg);
//        }
//        return RestResponseBo.ok();
//    }
}
