package com.mysoft.leistd.date.convert;

import com.mysoft.leistd.date.DateHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

/**
 * 表单数据日期格式转换
 */
@SuppressWarnings("all")
public class CustomFormDateConverter implements ConditionalGenericConverter {

    @Value("${spring.jackson.time-zone:}")
    private String timeZone;

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        Annotation annotation = targetType.getAnnotation(DateTimeFormat.class);
        return annotation == null;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        // 定义转换的源类型和目标类型
        return Collections.singleton(new ConvertiblePair(String.class, Date.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source instanceof String dateStr && StringUtils.isNotBlank(dateStr)) {
            if (StringUtils.isBlank(timeZone)) {
                return DateHelper.parse(dateStr);
            } else {
                return DateHelper.parse(dateStr, TimeZone.getTimeZone(timeZone));
            }
        }
        return null;
    }
}
