package com.mysoft.permission.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mysoft.leistd.domain.entities.auditing.CreationAuditedHasKeyEntity;
import com.mysoft.permission.user.event.UserCreatedEvent;
import com.mysoft.permission.user.valueobject.EnterpriseUserRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@TableName(value = "user")
public class User extends CreationAuditedHasKeyEntity<String> {

    /**
     * -- 自定义主键策略
     */
    @TableId(type = IdType.INPUT)
    private String id;

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    protected void setId(String id) {
        this.id = id;
        super.setId(id);
    }

    /**
     * 姓名
     */
    private String name;
    /**
     * 登录名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 启用/禁用
     */
    private Boolean isEnabled;

    /**
     * 是否管理员
     */
    private Boolean isAdmin;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否外部用户
     */
    private Boolean isOuter;

    @TableField(exist = false)
    private List<EnterpriseUserRole> enterpriseUserRoles = new ArrayList<>();

    public User(String id, String name, String username, String email, String phone, Boolean isOuter, String password) {
        setId(id);
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.isOuter = isOuter;
        this.password = password;
        enable();
        normal();
        addLocalEvent(new UserCreatedEvent(this));
    }

    public void enable() {
        this.isEnabled = true;
    }

    public void disable() {
        this.isEnabled = false;
    }

    public void admin() {
        this.isAdmin = true;
    }

    public void normal() {
        this.isAdmin = false;
    }
}
