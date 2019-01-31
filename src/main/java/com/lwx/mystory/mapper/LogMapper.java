package com.lwx.mystory.mapper;

import com.lwx.mystory.model.entity.Log;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 17:30 2019/1/30
 **/
@Component
public interface LogMapper  {

    List<Log> getVisitLogByAction(@Param("action") String action);

    boolean deleteLogById(@Param("id") int id);
}
