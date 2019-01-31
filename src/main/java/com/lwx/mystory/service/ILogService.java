package com.lwx.mystory.service;

import com.lwx.mystory.model.entity.Log;

import java.util.List;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 17:28 2019/1/30
 **/
public interface ILogService {

    List<Log> getVisitLogByAction(String action);

    String deleteLogById(int id);
}
