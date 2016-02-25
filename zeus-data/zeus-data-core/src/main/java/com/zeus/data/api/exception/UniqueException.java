package com.zeus.data.api.exception;

/**
 * 唯一性异常
 */
public class UniqueException extends BusinessException {

    protected UniqueException() {
    }

    public UniqueException(String message) {
        super(message);
    }

    public UniqueException(int code, String message) {
        super(message);
        this.code = code;
    }

    public UniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}