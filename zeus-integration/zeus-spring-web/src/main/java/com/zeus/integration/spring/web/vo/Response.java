package com.zeus.integration.spring.web.vo;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * 响应类
 *
 * @author quweixin
 * @since 2015/3/2 9:12
 */
public class Response {
    //状态码
    private int status;
    //返回信息
    private String message;
    //返回结果
    private Object result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * 成功
     */
    public Response success() {
        this.status = 200;
        this.message = "Operate success!";
        return this;
    }

    /**
     * 成功
     *
     * @param message 响应信息
     */
    public Response success(final String message) {
        update(200, message);
        return this;
    }

    /**
     * 失败
     *
     * @param message 响应信息
     */
    public Response fail(final String message) {
        update(300, message);
        return this;
    }

    /**
     * 失败
     *
     * @param e 异常对象
     */
    public Response fail(final Exception e) {
        if (e instanceof BindException) {
            List<FieldError> errors = ((BindException) e).getFieldErrors();
            StringBuilder message = new StringBuilder();
            for (FieldError error : errors) {
                message.append(error.getField() + ": " + error.getDefaultMessage() + "<br/>");
            }
            fail(message.toString());
        } else {
            String message = e.getMessage();
            if (message == null || message.isEmpty()) {
                message = "some error occurs";
            }
            fail(message);
        }
        return this;
    }

    /**
     * 更新状态
     *
     * @param status  状态
     * @param message 响应信息
     */
    public void update(final int status, final String message) {
        this.status = status;
        this.message = htmlEncode(message);
    }

    /**
     * 构建应答
     *
     * @return 应答
     */
    public static Response build() {
        return new Response();
    }

    /**
     * 构建应答
     *
     * @param result 结果
     * @return 应答
     */
    public static Response build(Object result) {
        Response response = new Response();
        response.setResult(result);
        return response;
    }

    /**
     * 编码
     *
     * @param text 文本
     * @return
     */
    protected static String htmlEncode(String text) {
        if (text == null) {
            return null;
        }

        String result = text.replace("\"", "&quot;");
        result = result.replace("<", "&lt;");
        result = result.replace(">", "&gt;");

        return result;
    }
}
