package com.mysoft.leistd.response;


import com.mysoft.leistd.dto.model.DataResponse;
import com.mysoft.leistd.dto.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

/**
 * 默认结果包装
 */
@Slf4j
public class DefaultResponseHandler implements ResponseHandler {

    @Override
    public Object handle(Class returnType, Object data) {
        if (returnType != null) {
            // ResponseEntity，则不包装(通常用于文件输出或者自定义返回结构)
            if (returnType == ResponseEntity.class || returnType.getGenericSuperclass() == ResponseEntity.class) {
                return data;
            }
            // Response，也不包装
            if (returnType == Response.class || returnType.getGenericSuperclass() == Response.class) {
                return data;
            }
        }
        if (data != null) {
            if (data.getClass() == ResponseEntity.class || data.getClass().getGenericSuperclass() == ResponseEntity.class) {
                return data;
            }
            if (data.getClass() == Response.class || data.getClass().getGenericSuperclass() == Response.class) {
                return data;
            }
        }
        return DataResponse.success(data);
    }
}
