package com.lwx.mystory.Mapper;

import com.lwx.mystory.model.entity.Visit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface VisitMapper {

    Visit getCountById(@Param("id") Integer id);

    void updateCountById(@Param("id") Integer id);
}
