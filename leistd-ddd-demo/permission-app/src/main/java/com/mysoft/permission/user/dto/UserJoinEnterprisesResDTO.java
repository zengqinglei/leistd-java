package com.mysoft.permission.user.dto;

import com.mysoft.leistd.dto.model.ResDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserJoinEnterprisesResDTO extends ResDTO {

    /**
     * 用户Id
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
     * 是否管理员
     */
    private Boolean isAdmin;

    /**
     * 加入的企业及角色信息集合
     */
    private List<EnterpriseUserRoleResDTO> joinEnterpriseRoles;
}
