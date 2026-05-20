package com.mysoft.leistd.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mysoft.leistd.enums.deserializer.BaseEnumDeserializer;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * 支持自定义Code的枚举基类
 */
@JsonDeserialize(using = BaseEnumDeserializer.class)
public interface BaseEnum<V extends Serializable, E extends Enum<E>> extends IEnum<E> {
    @JsonValue
    V getCode();

    static <B extends BaseEnum<V, E>, V extends Serializable, E extends Enum<E>> B codeOf(V code, Class<B> clazz) {
        if (code == null) {
            return null;
        }
        return Arrays.stream(clazz.getEnumConstants())
                .filter(e -> e.getCode().toString().equalsIgnoreCase(code.toString()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        MessageFormat.format(
                                "枚举类型【{0}】未匹配到对应的Code【{1}】",
                                clazz.getSimpleName(),
                                code)));
    }
}
