package com.mysoft.leistd.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mysoft.leistd.enums.deserializer.IEnumDeserializer;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * 枚举基类
 */
@JsonDeserialize(using = IEnumDeserializer.class)
public interface IEnum<E extends Enum<E>> {

    static <B extends IEnum<E>, E extends Enum<E>> B nameOf(String name, Class<B> clazz) {
        if (name == null) {
            return null;
        }
        return Arrays.stream(clazz.getEnumConstants())
                .filter(e -> e.toString().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        MessageFormat.format(
                                "枚举类型【{0}】未匹配到对应的Name【{1}】",
                                clazz.getSimpleName(),
                                name)));
    }
}
