package com.mysoft.leistd.enums.config;

import com.mysoft.leistd.enums.factory.BaseEnumConvertFactory;
import com.mysoft.leistd.enums.factory.IEnumConvertFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EnumWebMvcConfig implements WebMvcConfigurer {
    /**
     * 枚举类的转换器工厂 addConverterFactory
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new BaseEnumConvertFactory());
        registry.addConverterFactory(new IEnumConvertFactory());
    }
}
