package com.mysoft.permission.role.dto;

import com.mysoft.leistd.dto.model.ResDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EnterpriseRoleResDTO extends ResDTO {

    /**
     * 角色id
     */
    private Long id;
    /**
     * 企业id
     */
    private Long enterpriseId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 创建人用户名
     */
    private String creatorId;

    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private Date createdTime;
}
