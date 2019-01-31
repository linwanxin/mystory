package com.lwx.mystory.mapper;

import com.lwx.mystory.model.entity.Relationship;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 19:53 2019/1/9
 **/
public interface RelationshipMapper {

    /**
     * 根据id查询关系是否存在
     * @param cid
     * @param mid
     * @return
     */
    Long countById(@Param("cid") Integer cid,@Param("mid") Integer mid);


    void saveRelationship(Relationship relationship);

    void deleteRelationshipByCId(@Param("cid") Integer cid);

    List<Relationship> getRelationshipByMid(@Param("mid") Integer mid);
}
