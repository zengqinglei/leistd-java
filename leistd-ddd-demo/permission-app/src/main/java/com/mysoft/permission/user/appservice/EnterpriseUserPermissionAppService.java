package com.mysoft.permission.user.appservice;

import com.mysoft.permission.permission.dto.PermissionResDTO;
import com.mysoft.permission.user.dto.UserJoinEnterprisesResDTO;

import java.util.List;

public interface EnterpriseUserPermissionAppService {

    /**
     * 查询企业成员的权限数据
     *
     * @param enterpriseId 企业
     * @param userId       用户Id
     * @return 返回企业成员的权限数据
     */
    List<PermissionResDTO> listEnterpriseUserPermissions(Long enterpriseId, String userId);

    /**
     * 查询用户及可访问的企业Id集合
     *
     * @param userId 用户Id
     * @return 返回用户及可访问的企业Id集合
     */
    UserJoinEnterprisesResDTO listUserJoinEnterprises(String userId);
}
