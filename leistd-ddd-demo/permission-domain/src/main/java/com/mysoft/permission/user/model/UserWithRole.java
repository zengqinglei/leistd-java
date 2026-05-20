package com.mysoft.permission.user.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserWithRole  {

    /**
     * 用户id
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色Id
     */
    private Long roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 是否启用
     */
    private Boolean isEnabled;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 创建时间
     */
    private String creatorName;
}
