package com.zeus.data.api.exception;

/**
 * 服务的基础异常
 *
 * @author quweixin
 * @version 2.0.0
 * @since 2015-03-02
 */
public class ServiceException extends DataException {
    /**
     * 输入参数错误
     */
    public static final int BAD_REQUEST = 400;

    /**
     * 认证失败
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 服务禁止访问
     */
    public static final int FORBIDDEN = 403;

    /**
     * 数据不存在，如用户不存在，容器创建申请应答中指定的申请不存在等
     */
    public static final int NOT_FOUND = 404;

    /**
     * 发生未知错误
     */
    public static final int INTERNAL_SERVER_ERROR = 500;

    /**
     * 没有对应协议的实现
     */
    public static final int MAIL_PROTOCOL_NOT_IMPLEMENTED = 501;

    /**
     * 邮件发送失败
     */
    public static final int MAIL_SEND_FAILED = 502;

    /**
     * I/O错误
     */
    public static final int IO_ERROR = 503;

    /**
     * 并发事务错误
     */
    public static final int CONCURRENCY_ERROR = 504;

    protected ServiceException() {
    }

    public ServiceException(final int code, final String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(final int code, final String message, final Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
