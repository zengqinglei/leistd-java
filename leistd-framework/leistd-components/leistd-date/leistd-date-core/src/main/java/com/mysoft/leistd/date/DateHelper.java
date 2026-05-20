package com.mysoft.leistd.date;

import com.mysoft.leistd.exception.CommonException;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class DateHelper {
    private static final Map<String, DateTimeFormatter> LOCAL_DATETIME_FORMATS = new HashMap<>();
    private static final Map<String, DateFormat> DATE_FORMATS = new HashMap<>();

    static {
        LOCAL_DATETIME_FORMATS.put("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}(?::\\d{2}(?:\\.\\d{1,9})?)?(?:Z|[+-]\\d{2}:\\d{2})?$", DateTimeFormatter.ISO_DATE_TIME);
        LOCAL_DATETIME_FORMATS.put("^(?:Sun|Mon|Tue|Wed|Thu|Fri|Sat), \\d{2} (?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) \\d{4} \\d{2}:\\d{2}:\\d{2} (?:GMT|[+-]\\d{4})$", DateTimeFormatter.RFC_1123_DATE_TIME);
        DATE_FORMATS.put("^\\d{4}/\\d{1,2}/\\d{1,2}$", new SimpleDateFormat("yyyy/MM/dd"));
        DATE_FORMATS.put("^\\d{1,2}/\\d{1,2}/\\d{4}$", new SimpleDateFormat("MM/dd/yyyy"));
        DATE_FORMATS.put("^\\d{4}-\\d{1,2}$", new SimpleDateFormat("yyyy-MM"));
        DATE_FORMATS.put("^\\d{4}-\\d{1,2}-\\d{1,2}$", new SimpleDateFormat("yyyy-MM-dd"));
        DATE_FORMATS.put("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}$", new SimpleDateFormat("yyyy-MM-dd HH:mm"));
        DATE_FORMATS.put("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        DATE_FORMATS.put("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}$", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public static Date parse(String dateStr) {
        return parse(dateStr, null);
    }

    public static Date parse(String dateStr, TimeZone timeZone) {
        for (String pattern : LOCAL_DATETIME_FORMATS.keySet()) {
            if (dateStr.matches(pattern)) {
                DateTimeFormatter formatter = LOCAL_DATETIME_FORMATS.get(pattern);
                LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
                ZonedDateTime zonedDateTime = timeZone != null
                        ? dateTime.atZone(timeZone.toZoneId())
                        : dateTime.atZone(ZoneId.systemDefault());
                return Date.from(zonedDateTime.toInstant());
            }
        }
        for (String pattern : DATE_FORMATS.keySet()) {
            if (dateStr.matches(pattern)) {
                try {
                    DateFormat dateFormat = DATE_FORMATS.get(pattern);
                    if (timeZone != null) {
                        dateFormat.setTimeZone(timeZone);
                    }
                    return dateFormat.parse(dateStr);
                } catch (ParseException e) {
                    String error = MessageFormat.format(
                            "字符串【{0}】转换为时间格式【Date：{1}】失败",
                            dateStr,
                            pattern);
                    throw new CommonException(error, e);
                }
            }
        }
        throw new CommonException(MessageFormat.format("未匹配到字符串【{0}】的转换时间【Date】表达式", dateStr));
    }
}
