package com.mysoft.permission.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mysoft.leistd.mybatis.repositories.MybatisHasKeyRepositoryImpl;
import com.mysoft.permission.role.entity.EnterpriseRole;
import org.springframework.stereotype.Repository;

@Repository
public class EnterpriseRoleRepositoryImpl extends MybatisHasKeyRepositoryImpl<EnterpriseRole, Long> {
    public EnterpriseRoleRepositoryImpl(IService<EnterpriseRole> service, BaseMapper<EnterpriseRole> mapper) {
        super(service,mapper);
    }
}