package com.lwx.mystory.service;

import com.github.pagehelper.PageInfo;
import com.lwx.mystory.model.entity.Content;

import java.util.List;

/**
 * @Author:linwx
 * @Date；Created in 10:23 2018/12/2
 **/
public interface IContentService {
    /**
     * 根据类型获取文章列表
     * @param type
     * @param status
     * @return
     */
     List<Content> getContentsByType(String type, String status);
    /**
     * 根据分页信息获取文章
     * @param page
     * @param limit
     * @return
     */
    PageInfo<Content> getContentsByPageInfo(Integer page, Integer limit);

    /**
     * 更新文章内容
     * @param content
     * @return
     */
    boolean updateContent(Content content);

    /**
     * 根据ID获取文章
     * @param id
     * @return
     */
    Content getContentById(Integer id);

    /**
     *根据文章缩略名来查询文章
     * @param slug
     * @return
     */
    Content getContentBySlug(String slug);

    /**
     * 根绝特定条件来查询文章
     * @param type
     * @param page
     * @param limit
     * @return
     */
    PageInfo<Content> getContentsByCondititions(String type,Integer page,Integer limit);

    /**
     * 保存文章
     * @param content
     * @return
     */
    String saveContent(Content content);

    /**
     *
     * @param page
     * @param limit
     * @return
     */
    PageInfo<Content> getArticlesWithPage(Integer page,Integer limit);

    /**
     * 查询标签下面所属的文章
     * @param mid
     * @param page
     * @param limit
     * @return
     */
     PageInfo<Content> getTagArticles(Integer mid,int page, int limit);





}
