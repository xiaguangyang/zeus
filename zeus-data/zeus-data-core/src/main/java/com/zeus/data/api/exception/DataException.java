package com.zeus.data.api.exception;

/**
 * 数据异常
 */
public class DataException extends RuntimeException {
    // 代码
    protected int code;

    protected DataException() {
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(int code, String message) {
        super(message);
        this.code = code;
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}