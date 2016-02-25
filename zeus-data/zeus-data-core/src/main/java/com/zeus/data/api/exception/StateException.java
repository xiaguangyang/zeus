package com.zeus.data.api.exception;

/**
 * 状态异常
 * Created by quweixin on 15-4-21.
 */
public class StateException extends BusinessException {

    protected StateException() {
    }

    public StateException(String message) {
        super(message);
    }

    public StateException(int code, String message) {
        super(message);
        this.code = code;
    }

    public StateException(String message, Throwable cause) {
        super(message, cause);
    }

    public StateException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
