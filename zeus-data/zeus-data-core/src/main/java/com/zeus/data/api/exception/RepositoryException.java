package com.zeus.data.api.exception;

/**
 * 仓库异常
 */
public class RepositoryException extends DataException {

    protected RepositoryException() {
    }

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(int code, String message) {
        super(message);
        this.code = code;
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}