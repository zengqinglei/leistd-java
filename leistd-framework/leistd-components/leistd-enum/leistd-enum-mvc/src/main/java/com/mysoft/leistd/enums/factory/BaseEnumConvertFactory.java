package com.mysoft.leistd.enums.factory;

import com.mysoft.leistd.enums.BaseEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.text.MessageFormat;

@SuppressWarnings("all")
public class BaseEnumConvertFactory implements ConverterFactory<String, BaseEnum<?, ?>> {
    @Override
    public <T extends BaseEnum<?, ?>> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToBaseEnum(targetType);
    }

    private static class StringToBaseEnum<T extends BaseEnum<?, ?>> implements Converter<String, T> {
        final Class<T> enumType;

        StringToBaseEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            if (StringUtils.isBlank(source)) {
                return null;
            }
            T[] enums = enumType.getEnumConstants();
            for (T e : enums) {
                if (e.getCode().toString().equalsIgnoreCase(source) || e.toString().equalsIgnoreCase(source)) {
                    return e;
                }
            }
            throw new IllegalArgumentException(
                    MessageFormat.format(
                            "枚举类型【{0}】未匹配到对应的NameOrCode【{1}】",
                            enumType.getSimpleName(),
                            source));
        }
    }
}
