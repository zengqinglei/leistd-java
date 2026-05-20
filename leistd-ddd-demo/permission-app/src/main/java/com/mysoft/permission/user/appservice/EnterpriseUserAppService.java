package com.mysoft.permission.user.appservice;

import com.mysoft.leistd.dto.model.PageResDTO;
import com.mysoft.permission.user.dto.CreateEnterpriseUserReqDTO;
import com.mysoft.permission.user.dto.EnterpriseUserPageReqDTO;
import com.mysoft.permission.user.dto.EnterpriseUserWithRoleResDTO;

/**
 * 企业成员应用服务
 */
public interface EnterpriseUserAppService {

    /**
     * 分页查询企业成员数据（含角色信息）
     *
     * @param enterpriseId 企业Id
     * @param input        分页查询条件
     * @return 返回企业成员分页数据（含角色信息）
     */
    PageResDTO<EnterpriseUserWithRoleResDTO> pageEnterpriseUserWithRoles(Long enterpriseId, EnterpriseUserPageReqDTO input);

    /**
     * 获取指定的企业成员（含角色信息）
     *
     * @param userId       用户Id
     * @param enterpriseId 企业Id
     * @return 返回指定的企业成员（含角色信息）
     */
    EnterpriseUserWithRoleResDTO getEnterpriseUserWithRole(Long enterpriseId, String userId);

    /**
     * 添加企业成员并分配角色
     *
     * @param enterpriseId 企业Id
     * @param input        成员信息及所属角色
     * @return 返回新增的企业成员（含角色信息）
     */
    EnterpriseUserWithRoleResDTO createEnterpriseUser(Long enterpriseId, CreateEnterpriseUserReqDTO input);

    /**
     * 分配企业成员所属角色
     *
     * @param enterpriseId 企业Id
     * @param userId       用户Id
     * @param roleId       角色Id
     * @return 返回更新的企业成员（含角色信息）
     */
    EnterpriseUserWithRoleResDTO assignEnterpriseUserToRole(Long enterpriseId, String userId, Long roleId);

    /**
     * 删除企业成员
     *
     * @param enterpriseId 企业Id
     * @param userId       用户Id
     */
    void deleteEnterpriseUser(Long enterpriseId, String userId);


}
