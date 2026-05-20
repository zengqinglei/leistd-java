package com.mysoft.permission.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysoft.permission.mybatis.mapper.PermissionMapper;
import com.mysoft.permission.permission.entity.Permission;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> implements IService< Permission> {

}

