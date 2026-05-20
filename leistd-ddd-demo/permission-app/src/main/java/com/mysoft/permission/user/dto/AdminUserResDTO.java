package com.mysoft.permission.user.dto;

import com.mysoft.leistd.dto.model.ResDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AdminUserResDTO extends ResDTO {

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
     * 是否生效
     */
    private Boolean isEnabled;

    /**
     * 是否管理员
     */
    private Boolean isAdmin;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 创建人
     */
    private String creatorName;
}
