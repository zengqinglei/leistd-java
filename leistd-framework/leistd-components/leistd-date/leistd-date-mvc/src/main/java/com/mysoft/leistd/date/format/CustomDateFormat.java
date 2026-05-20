package com.mysoft.leistd.date.format;

import com.mysoft.leistd.date.DateHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class CustomDateFormat extends SimpleDateFormat {
    public CustomDateFormat() {
        super("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    }

    public CustomDateFormat(String pattern) {
        super(pattern);
    }

    @Override
    public Date parse(String dateStr) throws ParseException {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            return DateHelper.parse(dateStr, getTimeZone());
        } catch (Exception ex) {
            String error = MessageFormat.format(
                    "框架支持的日期格式序列化失败：{0}",
                    ex.getMessage());
            log.error(error, ex);
            return super.parse(dateStr);
        }
    }
}
