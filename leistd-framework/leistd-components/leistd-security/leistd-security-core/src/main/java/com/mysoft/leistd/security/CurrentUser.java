package com.mysoft.leistd.security;

import java.util.List;

/**
 * 当前用户信息
 */
public interface CurrentUser extends BaseCurrent {
    /**
     * 获取用户Id
     *
     * @return 返回用户Id
     */
    String getId();

    /**
     * 获取用户名
     *
     * @return 返回用户名
     */
    String getUsername();

    /**
     * 获取用户姓名
     *
     * @return 返回用户姓名
     */
    String getName();

    /**
     * 获取手机号
     *
     * @return 返回手机号
     */
    String getPhoneNumber();

    /**
     * 获取邮件
     *
     * @return 返回邮件
     */
    String getEmail();

    /**
     * 获取所属的角色
     *
     * @return 返回拥有的角色
     */
    List<String> getRoles();

    /**
     * 是否所属某个角色
     *
     * @param roleName 角色名称
     * @return 返回是或否
     */
    boolean isInRole(String roleName);
}
