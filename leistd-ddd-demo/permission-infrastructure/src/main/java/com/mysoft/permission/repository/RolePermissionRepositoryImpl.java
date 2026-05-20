package com.mysoft.permission.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mysoft.leistd.mybatis.repositories.MybatisHasKeyRepositoryImpl;
import com.mysoft.permission.role.valueobject.RolePermission;
import org.springframework.stereotype.Repository;

@Repository
public class RolePermissionRepositoryImpl extends MybatisHasKeyRepositoryImpl<RolePermission, Long> {
    public RolePermissionRepositoryImpl(IService<RolePermission> service, BaseMapper<RolePermission> mapper) {
        super(service, mapper);
    }
}