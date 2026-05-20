package com.mysoft.leistd.exception;

import lombok.Getter;

import java.text.MessageFormat;

public abstract class BusinessException extends CommonException {
    protected String codePrefix;

    /**
     * 异常码
     */
    @Getter
    int code;

    /**
     * 异常详情
     */
    @Getter
    String details;

    public BusinessException(String codePrefix, String messagePattern, Object... arguments) {
        super(arguments == null || arguments.length == 0 ? messagePattern : MessageFormat.format(messagePattern, arguments));
        this.codePrefix = codePrefix;
        this.code = Integer.parseInt(codePrefix + "00");
    }

    public BusinessException(String codePrefix, Throwable cause, String messagePattern, Object... arguments) {
        super(arguments == null || arguments.length == 0 ? messagePattern : MessageFormat.format(messagePattern, arguments), cause);
        this.codePrefix = codePrefix;
        this.code = Integer.parseInt(codePrefix + "00");
    }


    /**
     * 设置错误码
     *
     * @param code 错误码
     * @return 返回异常对象
     */
    public BusinessException code(String code) {
        this.code = Integer.parseInt(codePrefix + code);
        return this;
    }

    /**
     * 获取异常堆栈
     *
     * @return 返回异常堆栈
     */
    public String getStackTraceStr() {
        Throwable parentCause = super.getCause();
        if (parentCause == null) {
            return null;
        }
        StringBuilder detailsBuilder = new StringBuilder();
        detailsBuilder.append(parentCause);
        if (parentCause.getStackTrace() != null && parentCause.getStackTrace().length > 0) {
            detailsBuilder.append("\r\n");
            for (StackTraceElement stackTraceElement : parentCause.getStackTrace()) {
                detailsBuilder.append(MessageFormat.format("\tat {0}\r\n", stackTraceElement.toString()));
            }
        }
        return detailsBuilder.toString();
    }

    /**
     * 设置详细信息
     *
     * @param details 详细信息
     * @return 返回异常对象
     */
    public BusinessException details(String details) {
        this.details = details;
        return this;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " : [code=" + code + ", message=" + getMessage() + ", details=" + details + "]";
    }
}
