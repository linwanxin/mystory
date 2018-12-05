package com.lwx.mystory.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwx.mystory.Mapper.ContentMapper;
import com.lwx.mystory.model.dto.Types;
import com.lwx.mystory.model.entity.Content;
import com.lwx.mystory.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 11:25 2018/12/2
 **/
@Service
public class ContentServiceImpl implements IContentService {

    @Autowired
    private ContentMapper contentMapper;
    @Override
    public List<Content> getContentsByType(String type, String status) {
        return null;
    }

    @Override
    public PageInfo<Content> getContentsByPageInfo(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        //查询出所有文章
        List<Content> list = contentMapper.getContentsByType(Types.ARTICLE,Types.PUBLISH);
        PageInfo<Content> pageInfo = new PageInfo(list);
        return  pageInfo;
    }

    @Override
    public boolean updateContent(Content content) {
        return false;
    }

    @Override
    public Content getContentById(Integer id) {
        return null;
    }

    @Override
    public Content getContentBySlug(String slug) {
        return null;
    }

    @Override
    public PageInfo<Content> getContentsByCondititions(String type, Integer page, Integer limit) {
        return null;
    }

    @Override
    public String saveContent(Content content) {
        return null;
    }

    @Override
    public PageInfo<Content> getArticlesWithPage(Integer page, Integer limit) {
        return null;
    }

    @Override
    public PageInfo<Content> getTagArticles(Integer mid, int page, int limit) {
        return null;
    }
}
