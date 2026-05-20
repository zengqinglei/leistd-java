package com.mysoft.leistd.enums.enums;

import com.mysoft.leistd.enums.IEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 状态枚举
 */
@RequiredArgsConstructor
@Getter
public enum DemoStatusEnum implements IEnum<DemoStatusEnum> {
    WAITING("待运行"),
    RUNNING("运行中"),
    SUCCESS("运行成功"),
    FAILED("运行失败");

    private final String label;
}
