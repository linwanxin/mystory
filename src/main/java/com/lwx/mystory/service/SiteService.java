package com.lwx.mystory.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwx.mystory.mapper.ContentMapper;
import com.lwx.mystory.model.dto.Archive;
import com.lwx.mystory.model.dto.Types;
import com.lwx.mystory.model.entity.Comment;
import com.lwx.mystory.model.entity.Content;
import com.lwx.mystory.utils.DateKit;
import com.lwx.mystory.utils.MapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 23:54 2018/12/5
 **/
@Service
public class SiteService {

    @Autowired
    private ContentMapper contentMapper;

    //后台admin使用
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IContentService contentService;
    /**
     * 查询归档
     */
    public List<Archive> getArchives() {
        List<Archive> archiveList = contentMapper.getArchives(Types.ARTICLE, Types.PUBLISH);
        if (archiveList != null) {
            for (Archive archive : archiveList) {
                String date = archive.getDate();
                Date sd = DateKit.dateFormat(date, "yyyy年MM月");
                //开始时间和结束时间：（猜测是每个月的1号的unxi  到31天后的unix）debug一下
                int start = DateKit.getUnixTimeByDate(sd);
                int end = DateKit.getUnixTimeByDate(DateKit.dateAdd(DateKit.INTERVAL_MONTH, sd, 1)) - 1;
                //
                List<Content> contentList = contentMapper.getContentsByConditions(Types.ARTICLE, Types.PUBLISH, start, end);
                archive.setArticles(contentList);
            }
        }
        return archiveList;
    }

    /**
     * 获取最新收到的10条评论
     * @return
     */
    public List<Comment> getRecentComments(int limit){
        if(limit < 0 || limit > 10){
            limit = 10;
        }
        PageInfo<Comment> commentPageInfo =  commentService.getRecentComments(1,limit);
        return commentPageInfo.getList();
    }

    /**
     * 获取最近的文章
     */
    public List<Content> getContents(String type,int limit){
        if(limit < 0|| limit > 20){
            limit = 10;
        }
        if(Types.RECENT_ARTICLE.equals(type)){
            PageHelper.startPage(1,limit);
            List<Content> contentList = contentService.getContentsByType(Types.ARTICLE,Types.PUBLISH);
            PageInfo<Content> pageInfo = new PageInfo<>(contentList);
            return pageInfo.getList();
        }

        return new ArrayList<>();
    }

}
