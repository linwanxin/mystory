package com.lwx.mystory.service;

import com.lwx.mystory.model.entity.Meta;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Descripiton: 友情连接接口
 * @Author:linwx
 * @Date；Created in 23:58 2018/12/4
 **/
public interface IMetaService {

    /**
     * 根据类型获取友链
     *
     * @param type
     * @return
     */
    List<Meta> getMetasByType(String type);

    /**
     * 根据ID删除meta
     *
     * @param id
     */
    void delMetaById(Integer id);

    /**
     * 根据id获取meta
     *
     * @param id
     */
    Meta getMetaById(Integer id);

    void saveMeta(String type, String cname, Integer mid);

    /**
     * 根据条件查meta
     *
     * @param type
     * @param limit
     */
    List<Meta> getMetas(String type, Integer limit);

    Meta getMetaByTypeAndName(String type, String name);
}
