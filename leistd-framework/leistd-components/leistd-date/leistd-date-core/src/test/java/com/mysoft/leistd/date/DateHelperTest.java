package com.mysoft.leistd.date;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelperTest {
    @Test
    public void testParseDate() {
        // 定义日期时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        // ISO 8601
        Date date_iso_1 = DateHelper.parse("2024-01-02T09:10");
        Assert.assertEquals("2024-01-02T09:10:00.000+08:00", dateFormat.format(date_iso_1));
        Date date_iso_2 = DateHelper.parse("2024-01-02T09:10:59");
        Assert.assertEquals("2024-01-02T09:10:59.000+08:00", dateFormat.format(date_iso_2));
        Date date_iso_3_1 = DateHelper.parse("2023-08-30T15:05:31.5");
        Assert.assertEquals("2023-08-30T15:05:31.500+08:00", dateFormat.format(date_iso_3_1));
        Date date_iso_3_2 = DateHelper.parse("2023-08-30T15:05:31.51");
        Assert.assertEquals("2023-08-30T15:05:31.510+08:00", dateFormat.format(date_iso_3_2));
        Date date_iso_3 = DateHelper.parse("2024-01-02T09:10:59.088");
        Assert.assertEquals("2024-01-02T09:10:59.088+08:00", dateFormat.format(date_iso_3));
        Date date_iso_4 = DateHelper.parse("2024-04-09T10:58:25.8649532");
        Assert.assertEquals("2024-04-09T10:58:25.864+08:00", dateFormat.format(date_iso_4));
        Date date_iso_5 = DateHelper.parse("2024-04-09T10:58:25.123456789");
        Assert.assertEquals("2024-04-09T10:58:25.123+08:00", dateFormat.format(date_iso_5));
        Date date_iso_6 = DateHelper.parse("2024-01-02T09:10:59+08:00");
        Assert.assertEquals("2024-01-02T09:10:59.000+08:00", dateFormat.format(date_iso_6));
        Date date_iso_7 = DateHelper.parse("2024-01-02T09:10:59Z");
        Assert.assertEquals("2024-01-02T09:10:59.000+08:00", dateFormat.format(date_iso_7));
        Date date_iso_8 = DateHelper.parse("2024-01-02T09:10:59.088+08:00");
        Assert.assertEquals("2024-01-02T09:10:59.088+08:00", dateFormat.format(date_iso_8));
        Date date_iso_9 = DateHelper.parse("2024-01-02T09:10:59.088Z");
        Assert.assertEquals("2024-01-02T09:10:59.088+08:00", dateFormat.format(date_iso_9));
        Date date_iso_10 = DateHelper.parse("2024-04-09T10:58:25.123456789Z");
        Assert.assertEquals("2024-04-09T10:58:25.123+08:00", dateFormat.format(date_iso_10));
        Date date_iso_11 = DateHelper.parse("2024-04-09T10:58:25.123456789+08:00");
        Assert.assertEquals("2024-04-09T10:58:25.123+08:00", dateFormat.format(date_iso_11));

        // RFC 1123
        Date date_rfc_1 = DateHelper.parse("Wed, 10 Jul 2024 12:34:56 GMT");
        Assert.assertEquals("2024-07-10T12:34:56.000+08:00", dateFormat.format(date_rfc_1));
        Date date_rfc_2 = DateHelper.parse("Wed, 10 Jul 2024 12:34:56 +0800");
        Assert.assertEquals("2024-07-10T12:34:56.000+08:00", dateFormat.format(date_rfc_2));

        // 自定义
        Date date_custom_1 = DateHelper.parse("2024/01/02"); // yyyy/MM/dd
        Assert.assertEquals("2024-01-02T00:00:00.000+08:00", dateFormat.format(date_custom_1));
        Date date_custom_2 = DateHelper.parse("01/02/2024"); // yyyy/MM/dd
        Assert.assertEquals("2024-01-02T00:00:00.000+08:00", dateFormat.format(date_custom_2));
        Date date_custom_3 = DateHelper.parse("2024-01"); // yyyy-MM
        Assert.assertEquals("2024-01-01T00:00:00.000+08:00", dateFormat.format(date_custom_3));
        Date date_custom_4 = DateHelper.parse("2024-01-02"); // yyyy-MM-dd
        Assert.assertEquals("2024-01-02T00:00:00.000+08:00", dateFormat.format(date_custom_4));
        Date date_custom_5 = DateHelper.parse("2024-01-02 09:10"); // yyyy-MM-dd HH:mm
        Assert.assertEquals("2024-01-02T09:10:00.000+08:00", dateFormat.format(date_custom_5));
        Date date_custom_6 = DateHelper.parse("2024-01-02 09:10:59"); // yyyy-MM-dd HH:mm:ss
        Assert.assertEquals("2024-01-02T09:10:59.000+08:00", dateFormat.format(date_custom_6));
        Date date_custom_7 = DateHelper.parse("2024-01-02 09:10:59.088"); // yyyy-MM-dd HH:mm:ss.SSS
        Assert.assertEquals("2024-01-02T09:10:59.088+08:00", dateFormat.format(date_custom_7));
    }
}
