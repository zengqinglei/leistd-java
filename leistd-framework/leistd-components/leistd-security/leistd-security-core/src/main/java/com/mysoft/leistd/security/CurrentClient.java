package com.mysoft.leistd.security;

import java.util.List;

/**
 * 当前应用身份信息
 */
public interface CurrentClient extends BaseCurrent {
    /**
     * 获取ClientId
     *
     * @return 返回ClientId
     */
    String getClientId();

    /**
     * 获取Scope
     *
     * @return 返回Scope集合
     */
    List<String> getScope();
}
