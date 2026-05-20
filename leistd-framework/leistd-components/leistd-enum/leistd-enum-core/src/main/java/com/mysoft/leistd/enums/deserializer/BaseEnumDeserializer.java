package com.mysoft.leistd.enums.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.mysoft.leistd.enums.BaseEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.MessageFormat;

public class BaseEnumDeserializer extends JsonDeserializer<BaseEnum<?, ?>> implements ContextualDeserializer {
    private BaseEnum<?, ?>[] enums;

    @Override
    public BaseEnum<?, ?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String val = p.getText();
        if (StringUtils.isBlank(val)) {
            return null;
        }
        for (BaseEnum<?, ?> e : enums) {
            if (e.getCode().toString().equalsIgnoreCase(val) || e.toString().equalsIgnoreCase(val)) {
                return e;
            }
        }
        throw new IllegalArgumentException(
                MessageFormat.format(
                        "枚举类型【{0}】未匹配到对应的NameOrCode【{1}】",
                        enums.getClass().getComponentType().getSimpleName(),
                        val));
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        enums = (BaseEnum<?, ?>[]) ctxt.getContextualType().getRawClass().getEnumConstants();
        return this;
    }
}