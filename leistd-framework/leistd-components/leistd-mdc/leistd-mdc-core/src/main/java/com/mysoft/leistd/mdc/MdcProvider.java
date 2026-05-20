package com.mysoft.leistd.mdc;

import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认Mdc上下文提供服务
 */
public class MdcProvider implements AutoCloseable {
    private final ThreadLocal<List<String>> currentMdc = new ThreadLocal<>();

    /**
     * 添加键值到Mdc中
     *
     * @param key   键名
     * @param value 键值
     */
    public void add(String key, String value) {
        List<String> mdcKeys = currentMdc.get();
        if (mdcKeys == null) {
            mdcKeys = new ArrayList<>();
        }
        if (!mdcKeys.contains(key)) {
            mdcKeys.add(key);
        }
        MDC.put(key, value);
        currentMdc.set(mdcKeys);
    }

    /**
     * 清理所有的Mdc键值
     */
    @Override
    public void close() {
        List<String> mdcKeys = currentMdc.get();
        if (mdcKeys != null) {
            mdcKeys.forEach(MDC::remove);
        }
        currentMdc.remove();
    }
}
