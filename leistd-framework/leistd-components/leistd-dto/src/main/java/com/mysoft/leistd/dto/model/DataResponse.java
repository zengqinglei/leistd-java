package com.mysoft.leistd.dto.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据输出对象
 *
 * @param <T> 数据泛型
 */
@Setter
@Getter
public class DataResponse<T> extends Response {

    /**
     * 数据
     */
    private T data;


    /**
     * 成功输出，默认状态码为：0
     *
     * @return 返回输出对象
     */
    public static <T> DataResponse<T> success(T data) {
        DataResponse<T> response = new DataResponse<>();
        response.setData(data);
        return response;
    }
}
