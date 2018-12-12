package com.lwx.mystory.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 15:11 2018/12/11
 **/
public class TaleUtilsTest {

    @Test
    public void MD5encode() {
        String  ip = "44924646@qq.com";
        System.out.println(TaleUtils.MD5encode(ip));
    }
}