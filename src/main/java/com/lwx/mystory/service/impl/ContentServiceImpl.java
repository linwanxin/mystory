package com.lwx.mystory.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwx.mystory.constant.WebConstant;
import com.lwx.mystory.mapper.ContentMapper;
import com.lwx.mystory.mapper.MetaMapper;
import com.lwx.mystory.model.dto.Types;
import com.lwx.mystory.model.entity.Content;
import com.lwx.mystory.model.entity.User;
import com.lwx.mystory.service.IContentService;
import com.lwx.mystory.service.IMetaService;
import com.lwx.mystory.service.IRelationshipService;
import com.lwx.mystory.utils.DateKit;
import com.lwx.mystory.utils.TaleUtils;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private IMetaService metaService;
    @Autowired
    private MetaMapper metaMapper;
    @Autowired
    private IRelationshipService relationshipService;

    @Override
    public List<Content> getContentsByType(String type, String status) {
        return contentMapper.getContentsByType(type,status);
    }

    @Override
    public PageInfo<Content> getContentsByPageInfo(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        //查询出所有文章
        List<Content> list = contentMapper.getContentsByType(Types.ARTICLE, Types.PUBLISH);
        PageInfo<Content> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public boolean updateContent(Content content) {
        return contentMapper.updateContent(content);
    }

    @Override
    public Content getContentById(Integer id) {
        return contentMapper.getContentById(id);
    }

    @Override
    public Content getContentBySlug(String slug) {
        return contentMapper.getContentBySlug(slug);
    }



    @Override
    public PageInfo<Content> getContentsByUserId(String type, Integer page, Integer limit) {
        //这里一定是登陆后才能显示页面，user不可能为空
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer userId =  user.getId();

        PageHelper.startPage(page,limit);
        //如果userId是1的话，则代表admin，全部显示出来
        List<Content> contents = contentMapper.getContentsByUserId(type,userId == 1 ? -1: userId);
        PageInfo<Content> pageInfo = new PageInfo<>(contents);
        return pageInfo;
    }

    @Override
    public String deleteContentById(Integer cid) {
        Content content = contentMapper.getContentById(cid);
        if(content != null){
            //删除文章
            contentMapper.deleteContentById(cid);
            //删除关系
            relationshipService.deleteRelationshipByCId(cid);
            return WebConstant.SUCCESS_RESULT;
        }
        return "数据为空";

    }

    @Override
    @Transactional
    public void updateRelationShips(Integer cid, String tags, String categories) {
        //删除关系
        relationshipService.deleteRelationshipByCId(cid);
        //更新标签
        metaService.saveMeta(cid,tags,Types.TAG);
        //更新分类
        metaService.saveMeta(cid,categories,Types.CATEGORY);
    }

    @Override
    //还有后台写进来的（2部分来保存），所以把判断写在这里
    public String saveContent(Content content) {
        if(content == null){
            return "文章对象为空";
        }
        if(StringUtils.isBlank(content.getTitle())){
            return "对象标题不能为空";
        }
        if (StringUtils.isBlank(content.getContent())) {
            return "文章内容不能为空";
        }
        if(content.getTitle().length()> WebConstant.MAX_TITLE_COUNT){
            return "文章标题过长";
        }

        if(content.getContent().length() > WebConstant.MAX_TEXT_COUNT){
            return "文章内容过长";
        }
        if(null == content.getAuthorId()){
            return "请登录后发布文章";
        }
        if (StringUtils.isNotBlank(content.getSlug())) {
            if (content.getSlug().length() < 5) {
                return "路径太短了";
            }
            if (!TaleUtils.isPath(content.getSlug())) {
                return "您输入的路径不合法";
            }

            // 查询该文章路径是否存在<类型和缩略名>
            long count = contentMapper.getContentsBySlugType(content.getType(),content.getSlug());
            if (count > 0) {
                return "该路径已经存在，请重新输入";
            }
        } else {
            content.setSlug(null);
        }

        //
        content.setContent(EmojiParser.parseToAliases(content.getContent()));
        int time = DateKit.getCurrentUnixTime();
        content.setCreated(time);
        content.setModified(time);
        content.setHits(0);
        content.setCommentsNum(0);

        String tags = content.getTags();
        String categories = content.getCategories();
        //保存文章
        contentMapper.saveContent(content);
        Integer cid = content.getCid();
        //保存标签
        metaService.saveMeta(cid,tags,Types.TAG);
        //保存分类
        metaService.saveMeta(cid,categories,Types.CATEGORY);

        return WebConstant.SUCCESS_RESULT;
    }

    @Override
    public PageInfo<Content> getArticlesWithPage(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        //查询所有类型为page的文章
        List<Content> contentList = contentMapper.getContentsByUserId(Types.PAGE,1);
        PageInfo<Content> pageInfo = new PageInfo<>(contentList);
        return pageInfo;
    }

    @Override
    public PageInfo<Content> getTagArticles(Integer mid, int page, int limit) {
        PageHelper.startPage(page, limit);
        //查询标签下的文章数量
        int total = metaMapper.countWithSql(mid);
        List<Content> contentList = contentMapper.getContentsByMetaId(mid);
        PageInfo<Content> pageInfo = new PageInfo(contentList);
        pageInfo.setTotal(total);
        return pageInfo;
    }

    @Override
    public PageInfo<Content> getContentsByTitle(String title, int page, int limit) {
        PageHelper.startPage(page, limit);
        List<Content> contentList = contentMapper.getContentsByTitle(title, Types.ARTICLE, Types.PUBLISH);
        PageInfo<Content> pageInfo = new PageInfo(contentList);
        return pageInfo;
    }


}
