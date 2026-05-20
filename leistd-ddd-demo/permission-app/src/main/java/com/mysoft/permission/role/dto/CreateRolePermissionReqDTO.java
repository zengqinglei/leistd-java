package com.mysoft.permission.role.dto;

import com.mysoft.leistd.dto.model.ReqDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CreateRolePermissionReqDTO extends ReqDTO {

    /**
     * 权限码集合
     */
    @NotNull
    private List<String> permissionCodes;
}
