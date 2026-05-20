package com.mysoft.leistd.date.config;

import com.mysoft.leistd.date.convert.CustomFormDateConverter;
import com.mysoft.leistd.date.format.CustomDateFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DateWebMvcConfig implements WebMvcConfigurer {
    @Value("${spring.jackson.date-format:}")
    private String dateFormat;

    /**
     * 枚举类的转换器工厂 addConverterFactory
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CustomFormDateConverter());
    }

    /**
     * Json 数据多种日期格式转换支持
     */
    @Bean
    @ConditionalOnMissingBean
    public Jackson2ObjectMapperBuilderCustomizer jsonDateCustomizer() {
        return builder -> {
            if (StringUtils.isBlank(dateFormat)) {
                builder.dateFormat(new CustomDateFormat());
            }
        };
    }
}
