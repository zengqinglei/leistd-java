package com.mysoft.leistd.enums;

import com.mysoft.leistd.enums.enums.DemoIntegerEnum;
import com.mysoft.leistd.enums.enums.DemoStatusEnum;
import com.mysoft.leistd.enums.enums.DemoStringEnum;
import com.mysoft.leistd.jackson.JsonHelper;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class DemoEnumTest {
    @Test
    public void testConvertEnum() {
        DemoStringEnum strEnum = BaseEnum.codeOf("two", DemoStringEnum.class);
        Assert.assertEquals(strEnum, DemoStringEnum.TWO);

        DemoIntegerEnum intEnum = BaseEnum.codeOf(3, DemoIntegerEnum.class);
        Assert.assertEquals(intEnum, DemoIntegerEnum.THREE);

        try {
            BaseEnum.codeOf(7, DemoIntegerEnum.class);
            Assert.fail("枚举转换应当失败");
        } catch (Exception ex) {
            Assert.assertNotNull("枚举转换预期失败", ex);
        }
    }

    @Test
    public void testGetEnumJsonStr() {
        DemoIntegerEnum intEnum = BaseEnum.codeOf(2, DemoIntegerEnum.class);
        String enumValueStr = JsonHelper.toJSONString(intEnum);
        Assert.assertEquals(enumValueStr, "2");
    }

    @Test
    public void testConvertJsonStrToEnum() {
        String statusJsonStr = "\"SUCCESS\"";
        DemoStatusEnum statusEnum = JsonHelper.parseObject(statusJsonStr, DemoStatusEnum.class);
        Assert.assertEquals(statusEnum, DemoStatusEnum.SUCCESS);
        String intJsonStr = "\"3\"";
        DemoIntegerEnum intEnum = JsonHelper.parseObject(intJsonStr, DemoIntegerEnum.class);
        Assert.assertEquals(intEnum, DemoIntegerEnum.THREE);
        String strJsonStr = "\"two\"";
        DemoIntegerEnum towEnum = JsonHelper.parseObject(strJsonStr, DemoIntegerEnum.class);
        Assert.assertEquals(towEnum, DemoIntegerEnum.TWO);
    }
}
