package com.mysoft.permission.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysoft.permission.mybatis.mapper.EnterpriseUserRoleMapper;
import com.mysoft.permission.user.valueobject.EnterpriseUserRole;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseUserRoleService extends ServiceImpl<EnterpriseUserRoleMapper, EnterpriseUserRole> implements IService<EnterpriseUserRole> {

}

