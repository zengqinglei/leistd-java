package com.mysoft.permission.controller.role;


import com.mysoft.permission.controller.common.BaseController;
import com.mysoft.permission.role.appservice.EnterpriseRoleAppService;
import com.mysoft.permission.role.dto.EnterpriseRoleResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色管理
 */
@RestController
@RequiredArgsConstructor
public class EnterpriseRoleController extends BaseController {

    final EnterpriseRoleAppService enterpriseRoleAppService;

    /**
     * 查询当前企业下的所有角色
     */
    @GetMapping("/enterprises/{enterpriseId}/roles")
    public List<EnterpriseRoleResDTO> listRoles(@PathVariable Long enterpriseId) {
        return enterpriseRoleAppService.listEnterpriseRoles(enterpriseId);
    }

}
