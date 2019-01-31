package com.lwx.mystory.service;

import com.lwx.mystory.model.entity.Relationship;

import java.util.List;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 22:14 2019/1/6
 **/
public interface IRelationshipService {

    /**
     * 根据id来查询是否存在
     * @param cid
     * @param mid
     * @return
     */
    Long countById(Integer cid, int mid);

    //保存关系
    void saveRelationship(Relationship relationship);
    /**
     * 删除关系
     */
    void  deleteRelationshipByCId(Integer cid);

    /**
     * 根据id获取关系
     */
    List<Relationship> getRelationshipById(Integer mid);
}
