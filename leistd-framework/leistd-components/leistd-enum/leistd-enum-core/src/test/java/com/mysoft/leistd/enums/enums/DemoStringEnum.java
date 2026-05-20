package com.mysoft.leistd.enums.enums;

import com.mysoft.leistd.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 用于演示的枚举
 */
@RequiredArgsConstructor
@Getter
public enum DemoStringEnum implements BaseEnum<String, DemoStringEnum> {
    ONE("One", "测试1"),
    TWO("Two", "测试2"),
    THREE("Three", "测试3");

    private final String code;
    private final String label;
}
