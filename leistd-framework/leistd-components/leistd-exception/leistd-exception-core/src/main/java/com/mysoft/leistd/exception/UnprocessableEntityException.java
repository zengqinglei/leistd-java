package com.mysoft.leistd.exception;

import com.mysoft.leistd.jackson.JsonHelper;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * 实体验证错误异常，默认错误码：42200
 */
@Getter
public class UnprocessableEntityException extends BusinessException {

    /**
     * 实体验证错误集合
     */
    List<Map<String, String>> errors;

    public UnprocessableEntityException(List<Map<String, String>> errors, String messagePattern, Object... arguments) {
        super("422", messagePattern, arguments);

        this.errors = errors;
    }

    public UnprocessableEntityException(List<Map<String, String>> errors, Throwable cause, String messagePattern, Object... arguments) {
        super("422", cause, messagePattern, arguments);

        this.errors = errors;
    }

    /**
     * 设置实体验证错误集合
     *
     * @param errors 实体验证错误集合
     * @return 返回异常对象
     */
    public BusinessException errors(List<Map<String, String>> errors) {
        this.errors = errors;
        return this;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " : [code=" + code + ", message=" + getMessage() + ", details=" + details + ", errors=" + JsonHelper.toJSONString(errors) + "]";
    }
}
