package com.mysoft.permission.user.dto;

import com.mysoft.leistd.dto.model.ResDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EnterpriseUserWithRoleResDTO extends ResDTO {

    /**
     * 用户Id
     */
    private String id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 是否生效
     */
    private Boolean isEnabled;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 创建人
     */
    private String creatorName;
}
