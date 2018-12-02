package com.lwx.mystory.service;

import com.lwx.mystory.model.entity.Visit;

public interface IVisitService {
    Visit getCountById(Integer id);

    void updateCountById(Integer id);
}
