package com.zeus.data.api.exception;

/**
 * 唯一约束异常
 * Created by quweixin on 15-7-15.
 */
public class DuplicateKeyException extends RepositoryException {

    public DuplicateKeyException() {
    }

    public DuplicateKeyException(String message) {
        super(message);
    }

    public DuplicateKeyException(int code, String message) {
        super(code, message);
    }

    public DuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateKeyException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
