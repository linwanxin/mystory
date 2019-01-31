package com.lwx.mystory.service.impl;

import com.lwx.mystory.constant.WebConstant;
import com.lwx.mystory.mapper.LogMapper;
import com.lwx.mystory.model.entity.Log;
import com.lwx.mystory.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 17:29 2019/1/30
 **/
@Service
public class LogServiceImpl implements ILogService {
    @Autowired
    private LogMapper logMapper;
    @Override
    public List<Log> getVisitLogByAction(String action) {
        return logMapper.getVisitLogByAction(action);
    }

    @Override
    public String deleteLogById(int id) {
        boolean flag = logMapper.deleteLogById(id);
        if(flag){
            return WebConstant.SUCCESS_RESULT;
        }
        return "系统异常，删除失败！";
    }
}
