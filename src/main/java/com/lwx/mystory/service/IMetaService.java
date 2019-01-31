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
     *  + 获取文章类型category
     * @param type
     * @return
     */
    List<Meta> getMetasByType(String type);


    /**
     * 根据id获取meta
     *
     * @param id
     */
    Meta getMetaById(Integer id);


    /**
     * 根据条件查meta
     *
     * @param type
     * @param limit
     */
    List<Meta> getMetas(String type, Integer limit);

    Meta getMetaByTypeAndName(String type, String name);

    /**
     * 保存标签或者分类
     */
    void saveMeta(Integer cid,String tags,String type);

    /**
     * 后台保存分类,形成重载
     */
    void saveMeta(String type,String name,Integer mid);

}
