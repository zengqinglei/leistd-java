package com.mysoft.permission.user.domainservice;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mysoft.leistd.domain.repositories.HasKeyRepository;
import com.mysoft.leistd.exception.NotFoundErrorException;
import com.mysoft.permission.role.entity.EnterpriseRole;
import com.mysoft.permission.user.entity.User;
import com.mysoft.permission.user.valueobject.EnterpriseUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EnterpriseUserDomainService {

    final HasKeyRepository<EnterpriseRole, Long> roleRepository;
    final HasKeyRepository<EnterpriseUserRole, Long> enterpriseUserRoleRepository;


    public EnterpriseRole assignToRole(User user, Long enterpriseId, Long roleId) {
        EnterpriseRole role = roleRepository.getById(roleId);
        if (role == null) {
            throw new NotFoundErrorException("角色【{0}】不存在或已删除", roleId);
        }
        LambdaQueryWrapper<EnterpriseUserRole> queryWrapper = new LambdaQueryWrapper<EnterpriseUserRole>()
                .eq(EnterpriseUserRole::getEnterpriseId, enterpriseId)
                .eq(EnterpriseUserRole::getUserId, user.getId());
        enterpriseUserRoleRepository.delete(queryWrapper);
        user.getEnterpriseUserRoles().add(new EnterpriseUserRole(enterpriseId, user.getId(), roleId));
        // 保存用户和角色关系
        enterpriseUserRoleRepository.insertBatch(user.getEnterpriseUserRoles());
        return role;
    }
}
