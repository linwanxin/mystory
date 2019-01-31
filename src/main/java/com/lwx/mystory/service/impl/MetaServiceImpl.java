package com.lwx.mystory.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwx.mystory.exception.TipException;
import com.lwx.mystory.mapper.MetaMapper;
import com.lwx.mystory.model.entity.Content;
import com.lwx.mystory.model.entity.Meta;
import com.lwx.mystory.model.entity.Relationship;
import com.lwx.mystory.service.IMetaService;
import com.lwx.mystory.service.IRelationshipService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Descripiton:友链实现类
 * @Author:linwx
 * @Date；Created in 22:15 2018/12/5
 **/
@Service
public class MetaServiceImpl implements IMetaService {
    @Autowired
    private MetaMapper metaMapper;
    @Autowired
    private IRelationshipService relationshipService;

    @Override
    public List<Meta> getMetasByType(String type) {
        return metaMapper.getMetasByType(type);
    }


    @Override
    public Meta getMetaById(Integer id) {
        return null;
    }

    @Override
    public void saveMeta(Integer cid, String names,String type) {
        if(null == cid){
            throw  new TipException("项目关联id不能为空");
        }
        if(StringUtils.isNotBlank(names) && StringUtils.isNotBlank(type)){
            String[] nameArr = StringUtils.split(names,",");
            for(String name  : nameArr){
                this.saveOrUpdate(cid,name,type);
            }
        }
    }

    /**
     * 用于后台保存分类:需要好好理解这里
     * @param type
     * @param name
     * @param mid
     */
    @Override
    public void saveMeta(String type, String name, Integer mid) {
        if(StringUtils.isNotBlank(type) && StringUtils.isNotBlank(name)){
            List<Meta> metas = metaMapper.selectMetaListByConditions(type,name);
            if(metas.size() !=0){
                throw new TipException("已经存在该项");
            }else{
                Meta meta = new Meta();
                meta.setName(name);
                if(null != mid){
                    meta.setMid(mid);
                    metaMapper.updateMeta(meta);
                }else{
                    meta.setType(type);
                    metaMapper.saveMeta(meta);
                }
            }
        }
    }

    private void saveOrUpdate(Integer cid,String name,String type){
        //根据类型和名称查询metas
        List<Meta> metas = metaMapper.selectMetaListByConditions(type,name);
        int mid;
        Meta meta;
        if(metas.size() == 1){
            meta = metas.get(0);
            mid = meta.getMid();
        }else if(metas.size() > 1 ){
            throw  new TipException("查询到多条数据");
        }else{
            meta = new Meta();
            meta.setSlug(name);
            meta.setName(name);
            meta.setType(type);
            metaMapper.saveMeta(meta);
            mid = meta.getMid();
        }
        if(mid != 0){
            Long count = relationshipService.countById(cid,mid);
            if(count == 0){
                Relationship relationship = new Relationship();
                relationship.setCid(cid);
                relationship.setMid(mid);
                relationshipService.saveRelationship(relationship);
            }
        }
    }


    @Override
    public List<Meta> getMetas(String type, Integer limit) {
        PageHelper.startPage(1, limit);
        List<Meta> metas = metaMapper.getMetasBySql(type);
        PageInfo<Meta> pageInfo = new PageInfo(metas);
        return pageInfo.getList();
    }

    @Override
    public Meta getMetaByTypeAndName(String type, String name) {
        return metaMapper.getMetaByTypeAndName(type, name);
    }
}
