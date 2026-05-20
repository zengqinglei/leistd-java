package com.mysoft.permission.controller.user;

import com.mysoft.leistd.security.CurrentUser;
import com.mysoft.permission.controller.common.BaseController;
import com.mysoft.permission.permission.dto.PermissionResDTO;
import com.mysoft.permission.user.appservice.EnterpriseUserPermissionAppService;
import com.mysoft.permission.user.dto.UserJoinEnterprisesResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EnterpriseUserPermissionController extends BaseController {

    final EnterpriseUserPermissionAppService enterpriseUserPermissionAppService;
    final CurrentUser currentUser;

    /**
     * 查询企业成员的权限数据
     */
    @GetMapping("/enterprises/{enterpriseId}/users/{userId}/permissions")
    public List<PermissionResDTO> listEnterpriseUserPermissions(@PathVariable Long enterpriseId, @PathVariable String userId) {
        return enterpriseUserPermissionAppService.listEnterpriseUserPermissions(enterpriseId, userId);
    }

    /**
     * 查询用户加入的企业角色信息集合
     */
    @GetMapping("/current-user/join-enterprises")
    public UserJoinEnterprisesResDTO listUserJoinEnterprises() {
        String userId = currentUser.getId();
        String username = currentUser.getUsername();
        String email = currentUser.getEmail();
        String phone = currentUser.getPhoneNumber();
        List<String> roles = currentUser.getRoles();
        Date issuedAt = currentUser.getIssuedAt();
        Date notBefore = currentUser.getNotBefore();
        Date expiration = currentUser.getExpiration();
        return enterpriseUserPermissionAppService.listUserJoinEnterprises(currentUser.getId());
    }
}
