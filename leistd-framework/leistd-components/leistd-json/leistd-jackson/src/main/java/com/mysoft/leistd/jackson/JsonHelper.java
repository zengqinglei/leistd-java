package com.mysoft.leistd.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysoft.leistd.context.ApplicationContextHelper;
import com.mysoft.leistd.exception.CommonException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.MessageFormat;

/**
 * Json 序列化帮助类
 */
public class JsonHelper {
    static final Log log = LogFactory.getLog(JsonHelper.class);

    private static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = ApplicationContextHelper.getBean(ObjectMapper.class);
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    /**
     * 序列化为 Json 字符串
     *
     * @param value 对象
     * @return 返回 Json 字符串
     */
    public static String toJSONString(Object value) {
        try {
            return getObjectMapper().writeValueAsString(value);
        } catch (Exception e) {
            String error = MessageFormat.format("对象【{0}】序列化JSON字符串失败", value.getClass().getSimpleName());
            log.error(error, e);
            throw new CommonException(error, e);
        }
    }

    /**
     * 反序列化为对象
     *
     * @param jsonStr Json 字符串
     * @param cls     对象 Class
     * @param <T>     对象类型
     * @return 返回对象
     */
    public static <T> T parseObject(String jsonStr, Class<T> cls) {
        try {
            return getObjectMapper().readValue(jsonStr, cls);
        } catch (Exception e) {
            String error = MessageFormat.format(
                    "JSON反序列化对象【{0}】失败",
                    cls.getSimpleName());
            log.error(error, e);
            throw new CommonException(error, e);
        }
    }

    /**
     * 反序列化对象
     *
     * @param jsonStr       Json 字符拆
     * @param typeReference 引用类型
     * @param <T>           对象类型
     * @return 返回对象
     */
    public static <T> T parseObject(String jsonStr, TypeReference<T> typeReference) {
        try {
            return getObjectMapper().readValue(jsonStr, typeReference);
        } catch (Exception e) {
            String error = MessageFormat.format(
                    "JSON反序列化对象【{0}】失败",
                    typeReference.getType().getTypeName());
            log.error(error, e);
            throw new CommonException(error, e);
        }
    }
}
