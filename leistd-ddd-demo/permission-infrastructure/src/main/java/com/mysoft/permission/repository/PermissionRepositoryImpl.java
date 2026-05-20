package com.mysoft.permission.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mysoft.leistd.mybatis.repositories.MybatisHasKeyRepositoryImpl;
import com.mysoft.permission.permission.entity.Permission;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRepositoryImpl extends MybatisHasKeyRepositoryImpl<Permission, Long> {
    public PermissionRepositoryImpl(IService<Permission> service, BaseMapper<Permission> mapper) {
        super(service, mapper);
    }
}