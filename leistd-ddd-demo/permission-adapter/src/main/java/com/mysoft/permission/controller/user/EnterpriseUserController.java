package com.mysoft.permission.controller.user;


import com.mysoft.leistd.dto.model.PageResDTO;
import com.mysoft.permission.controller.common.BaseController;
import com.mysoft.permission.user.appservice.EnterpriseUserAppService;
import com.mysoft.permission.user.dto.CreateEnterpriseUserReqDTO;
import com.mysoft.permission.user.dto.EnterpriseUserPageReqDTO;
import com.mysoft.permission.user.dto.EnterpriseUserWithRoleResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 企业成员管理
 */
@RestController
@RequiredArgsConstructor
public class EnterpriseUserController extends BaseController {

    final EnterpriseUserAppService enterpriseUserAppService;

    /**
     * 分页查询企业成员列表
     */
    @GetMapping("/enterprises/{enterpriseId}/users")
    public PageResDTO<EnterpriseUserWithRoleResDTO> pageEnterpriseUserWithRoles(@PathVariable Long enterpriseId, @Validated EnterpriseUserPageReqDTO input) {
        return enterpriseUserAppService.pageEnterpriseUserWithRoles(enterpriseId, input);
    }

    /**
     * 查询企业成员详情
     */
    @GetMapping("/enterprises/{enterpriseId}/users/{userId}")
    public EnterpriseUserWithRoleResDTO getEnterpriseUserWithRole(@PathVariable Long enterpriseId, @PathVariable String userId) {
        return enterpriseUserAppService.getEnterpriseUserWithRole(enterpriseId, userId);
    }

    /**
     * 新增企业成员
     */
    @PostMapping("/enterprises/{enterpriseId}/users")
    public EnterpriseUserWithRoleResDTO createEnterpriseUser(@PathVariable Long enterpriseId, @Validated @RequestBody CreateEnterpriseUserReqDTO input) {
        return enterpriseUserAppService.createEnterpriseUser(enterpriseId, input);
    }

    /**
     * 分配企业成员所属角色
     */
    @PutMapping("/enterprises/{enterpriseId}/users/{userId}/roles/{roleId}")
    public EnterpriseUserWithRoleResDTO assignEnterpriseUserToRole(@PathVariable Long enterpriseId, @PathVariable String userId, @PathVariable Long roleId) {
        return enterpriseUserAppService.assignEnterpriseUserToRole(enterpriseId, userId, roleId);
    }

    /**
     * 删除企业成员
     */
    @DeleteMapping("/enterprises/{enterpriseId}/users/{userId}")
    public void deleteEnterpriseUser(@PathVariable Long enterpriseId, @PathVariable String userId) {
        enterpriseUserAppService.deleteEnterpriseUser(enterpriseId, userId);
    }

}
