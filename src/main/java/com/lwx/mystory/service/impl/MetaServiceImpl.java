package com.lwx.mystory.service.impl;

import com.lwx.mystory.Mapper.MetaMapper;
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
        return null;
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
    public List<Meta> getMetaList(String type, String orderby, Integer limit) {
        return null;
    }

    @Override
    public Meta getMeta(String type, String name) {
       return  metaMapper.getMate(type,name);
    }
}
