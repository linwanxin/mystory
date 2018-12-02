package com.lwx.mystory.service.impl;

import com.lwx.mystory.Mapper.VisitMapper;
import com.lwx.mystory.model.entity.Visit;
import com.lwx.mystory.service.IVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitServiceImpl implements IVisitService {
    @Autowired
    private VisitMapper visitMapper;

    @Override
    public Visit getCountById(Integer id) {
        return visitMapper.getCountById(id);
    }

    @Override
    public void updateCountById(Integer id) {
        visitMapper.updateCountById(id);
    }
}
