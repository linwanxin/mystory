package com.lwx.mystory.utils;

import org.junit.Test;

import java.util.UUID;


/**
 * @Descripiton:
 * @Author:linwx
 * @Date；Created in 15:11 2018/12/11
 **/
public class TaleUtilsTest {

    @Test
    public void MD5encode() {
        String ip = "44924646@qq.com";
        System.out.println(TaleUtils.MD5encode(ip));
    }

    /**
     * 测试UUID是不是requestNonce（随机数）
     */
    @Test
    public void nonce() {
        String s = UUID.randomUUID().toString();
        String s2 = s.replaceAll("-", "");
        System.out.println(s);
        System.out.println(s2);
        System.out.println("70476487149327779303600443043791");
    }
    @Test
    public void testMD5(){
        System.out.println(TaleUtils.MD5encode("admin123456"));
    }



    /**
     * 验证结果是java和py是一致的！
     */
//    @Test
//    public void sha1(){
//        String genSigrn = "genSign = :/v3/post/recommendedfirstPostId359087679topPostId3588969359047334W1LlVeMSNgoDACDt8LrjOpU410000003Yh@40@w^5P2s201812171745f4debb2698754f86b71890a5524376943018";
//
//        System.out.println(DigestUtils.sha1Hex(genSigrn));
//    }
}