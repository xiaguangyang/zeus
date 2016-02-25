package com.zeus.data.api.exception;

/**
 * 不存在异常
 * Created by quweixin on 15-4-21.
 */
public class NotFoundException extends BusinessException {

    protected NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(int code, String message) {
        super(message);
        this.code = code;
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
