package com.mysoft.leistd.security;

import java.util.Date;

public interface BaseCurrent {
    /**
     * 是否已授权
     *
     * @return 返回是或否
     */
    boolean isAuthenticated();

    /**
     * 获取授权时间
     *
     * @return 返回授权时间
     */
    Date getIssuedAt();

    /**
     * 在此时间之前不接受处理
     *
     * @return 在此之前不接受处理时间
     */
    Date getNotBefore();

    /**
     * 获取过期时间
     *
     * @return 返回过期时间
     */
    Date getExpiration();
}
