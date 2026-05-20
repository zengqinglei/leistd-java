package com.mysoft.leistd.dto.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class Response extends ResDTO {

    /**
     * 状态码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 错误详情
     */
    private String details;

    /**
     * 实体验证错误信息
     */
    private List<Map<String, String>> errors;

    /**
     * 成功输出，默认状态码为：0
     *
     * @return 返回输出对象
     */
    public static Response success() {
        return new Response();
    }

    /**
     * 失败输出
     *
     * @return 返回输出对象
     */
    public static Response failure(int code, String message) {
        Response response = new Response();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    @Override
    public String toString() {
        return "Response [code=" + code + ", message=" + message + ", details=" + details + "]";
    }
}
