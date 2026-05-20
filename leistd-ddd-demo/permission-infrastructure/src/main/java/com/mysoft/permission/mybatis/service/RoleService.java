package com.mysoft.permission.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysoft.permission.mybatis.mapper.RoleMapper;
import com.mysoft.permission.role.entity.EnterpriseRole;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends ServiceImpl<RoleMapper, EnterpriseRole> implements IService<EnterpriseRole> {

}

