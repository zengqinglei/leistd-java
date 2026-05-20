package com.mysoft.permission.user.dto;

import com.mysoft.leistd.dto.model.ResDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * 企业用户角色关系信息
 */
@Getter
@Setter
public class EnterpriseUserRoleResDTO extends ResDTO {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 企业Id
     */
    private Long enterpriseId;
}
