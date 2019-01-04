package com.lwx.mystory.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Descripiton: 加密工具
 * @Author:linwx
 * @Date；Created in 17:02 2018/12/31
 **/
public class MD5Utils {
    private static final String SALT = "yulenka";

    private static final String ALGORITH_NAME = "md5";

    //哈希次数
    private static final int HASH_ITERATIONS = 2;

    public static String encrypt(String username,String passwd){
        String encryptPasswd = new SimpleHash(ALGORITH_NAME,passwd,ByteSource.Util.bytes(username + SALT),HASH_ITERATIONS).toHex();
        return encryptPasswd;
    }

    //用户名不一样得话，密码一样，最终结果不一样！
    public static void main(String[] args) {
        System.out.println(MD5Utils.encrypt("admin", "123456"));
    }

}
