package com.lwx.mystory.service.impl;

import com.lwx.mystory.mapper.RelationshipMapper;
import com.lwx.mystory.model.entity.Relationship;
import com.lwx.mystory.service.IRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 19:52 2019/1/9
 **/
@Service
public class RelationshipServiceImpl implements IRelationshipService {
    @Autowired
    RelationshipMapper relationshipMapper;

    @Override
    public Long countById(Integer cid, int mid) {
        return relationshipMapper.countById(cid,mid);
    }

    @Override
    public void saveRelationship(Relationship relationship) {
        relationshipMapper.saveRelationship(relationship);
    }

    @Override
    public void deleteRelationshipByCId(Integer cid) {
        relationshipMapper.deleteRelationshipByCId(cid);
    }

    //
    @Override
    public List<Relationship> getRelationshipById(Integer mid) {
        return relationshipMapper.getRelationshipByMid(mid);
    }


}
