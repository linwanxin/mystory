package com.lwx.mystory.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwx.mystory.mapper.MetaMapper;
import com.lwx.mystory.model.entity.Meta;
import com.lwx.mystory.service.IMetaService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<Meta> getMetasByType(String type) {
        return metaMapper.getMetasByType(type);
    }

    @Override
    public void delMetaById(Integer id) {

    }

    @Override
    public Meta getMetaById(Integer id) {
        return null;
    }

    @Override
    public void saveMeta(String type, String cname, Integer mid) {

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
