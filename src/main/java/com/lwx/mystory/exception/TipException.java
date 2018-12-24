package com.lwx.mystory.exception;

/**
 * @Descripiton: 自定义异常类
 * @Author:linwx
 * @Date；Created in 15:02 2018/12/6
 **/
public class TipException extends RuntimeException {
    public TipException() {
    }

    public TipException(String message) {
        super(message);
    }

    public TipException(String message, Throwable cause) {
        super(message, cause);
    }

    public TipException(Throwable cause) {
        super(cause);
    }
}
