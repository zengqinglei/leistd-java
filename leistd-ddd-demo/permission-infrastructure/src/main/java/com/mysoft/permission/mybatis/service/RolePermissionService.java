package com.mysoft.permission.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysoft.permission.mybatis.mapper.RolePermissionMapper;
import com.mysoft.permission.role.valueobject.RolePermission;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermission> implements IService< RolePermission> {

}

