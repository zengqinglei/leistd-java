package com.mysoft.leistd.enums.factory;

import com.mysoft.leistd.enums.IEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.text.MessageFormat;

@SuppressWarnings("all")
public class IEnumConvertFactory implements ConverterFactory<String, IEnum<?>> {
    @Override
    public <T extends IEnum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToIEnum(targetType);
    }

    private static class StringToIEnum<T extends IEnum<?>> implements Converter<String, T> {
        final Class<T> enumType;

        StringToIEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            if (StringUtils.isBlank(source)) {
                return null;
            }
            T[] enums = enumType.getEnumConstants();
            for (T e : enums) {
                if (e.toString().equalsIgnoreCase(source)) {
                    return e;
                }
            }
            throw new IllegalArgumentException(
                    MessageFormat.format(
                            "枚举类型【{0}】未匹配到对应的Name【{1}】",
                            enumType.getSimpleName(),
                            source));
        }
    }
}
