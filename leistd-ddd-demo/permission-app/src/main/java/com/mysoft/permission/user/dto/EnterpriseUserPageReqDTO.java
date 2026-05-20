package com.mysoft.permission.user.dto;

import com.mysoft.leistd.dto.model.PageReqDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnterpriseUserPageReqDTO extends PageReqDTO {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色名称（精准匹配）
     */
    private String roleName;

    /**
     * 关键词
     */
    private String keyword;

}
