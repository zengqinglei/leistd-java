package com.mysoft.permission.permission.appservice.impl;

import com.mysoft.leistd.domain.repositories.HasKeyRepository;
import com.mysoft.permission.permission.appservice.PermissionAppService;
import com.mysoft.permission.permission.convert.PermissionConvert;
import com.mysoft.permission.permission.dto.PermissionResDTO;
import com.mysoft.permission.permission.entity.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionAppServiceImpl implements PermissionAppService {

    final HasKeyRepository<Permission, Long> permissionRepository;
    final PermissionConvert permissionConvert;

    /**
     *  查询所有权限code列表
     */
    @Override
    public List<PermissionResDTO> listAllPermission() {
        return permissionConvert.toResDTOList(permissionRepository.list());
    }

}
