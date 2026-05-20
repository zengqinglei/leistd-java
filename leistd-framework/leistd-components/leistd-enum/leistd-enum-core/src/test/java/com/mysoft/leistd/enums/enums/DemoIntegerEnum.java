package com.mysoft.leistd.enums.enums;

import com.mysoft.leistd.enums.DefaultEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 用于演示的枚举
 */
@RequiredArgsConstructor
@Getter
public enum DemoIntegerEnum implements DefaultEnum<DemoIntegerEnum> {
    ONE(1, "测试1"),
    TWO(2, "测试2"),
    THREE(3, "测试3");

    private final Integer code;
    private final String label;
}
