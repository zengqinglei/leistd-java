package com.mysoft.permission.permission.dto;

import com.mysoft.leistd.dto.model.ResDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionResDTO extends ResDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限显示名称
     */
    private String displayName;

    /**
     * 权限编码
     */
    private String code;

}
