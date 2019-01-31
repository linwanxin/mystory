package com.lwx.mystory.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwx.mystory.constant.WebConstant;
import com.lwx.mystory.controller.BaseController;
import com.lwx.mystory.model.bo.RestResponseBo;
import com.lwx.mystory.model.entity.Comment;
import com.lwx.mystory.model.entity.User;
import com.lwx.mystory.service.ICommentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Descripiton:评论管理
 * @Author:linwx
 * @Date；Created in 16:44 2019/1/27
 **/
@Controller
@RequestMapping("admin/comments")
public class CommentController extends BaseController {

    @Autowired
    private ICommentService commentService;

    /**
     *
     * @param page
     * @param limit
     * @param request
     * @return
     */
    @GetMapping("")
    public String index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int limit,
                        HttpServletRequest request){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(page,limit);
        //查询非登陆人的评论
        List<Comment> comments = commentService.selectCommentsByAuthorId(user.getId());
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        request.setAttribute("comments",pageInfo);
        return "admin/comment_list";
    }

    /**
     * 删除评论
     * @param coid
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    @RequiresRoles("admin")
    public RestResponseBo deleteCommentById(@RequestParam Integer coid){
        String result = commentService.delCommentById(coid);
        if(WebConstant.SUCCESS_RESULT.equals(result)){
            return RestResponseBo.ok();
        }
        return RestResponseBo.fail(result);
    }

    @PostMapping("/status")
    @ResponseBody
    @RequiresRoles("admin")
    //前台过来了status但是没用到
    public RestResponseBo updateCommentStatus(@RequestParam Integer coid,
                                              @RequestParam String status){
        Comment comment = new Comment();
        comment.setStatus(status);
        comment.setCoid(coid);
        commentService.updateComment(comment);
        return RestResponseBo.ok();
    }

}
