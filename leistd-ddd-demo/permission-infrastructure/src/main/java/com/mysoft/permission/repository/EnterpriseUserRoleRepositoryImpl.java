package com.mysoft.permission.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mysoft.leistd.domain.entities.page.PageQuery;
import com.mysoft.leistd.domain.entities.page.PageResult;
import com.mysoft.leistd.mybatis.repositories.MybatisHasKeyRepositoryImpl;
import com.mysoft.permission.mybatis.mapper.EnterpriseUserRoleMapper;
import com.mysoft.permission.user.model.UserWithRole;
import com.mysoft.permission.user.repository.EnterpriseUserRoleRepository;
import com.mysoft.permission.user.valueobject.EnterpriseUserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class EnterpriseUserRoleRepositoryImpl extends MybatisHasKeyRepositoryImpl<EnterpriseUserRole, Long> implements EnterpriseUserRoleRepository {
    final EnterpriseUserRoleMapper enterpriseUserRoleMapper;

    public EnterpriseUserRoleRepositoryImpl(IService<EnterpriseUserRole> service, EnterpriseUserRoleMapper mapper) {
        super(service, mapper);
        this.enterpriseUserRoleMapper = mapper;
    }

    @Override
    public PageResult<UserWithRole> pageUserWithRole(Long enterpriseId, Long roleId, String roleName, String keyword, PageQuery pageQuery) {
        QueryWrapper<Object> queryWrapper = new QueryWrapper<>()
                .eq("eur.enterprise_id", enterpriseId)
                .eq(roleId != null, "eur.role_id", roleId)
                .eq(StringUtils.isNotBlank(roleName), "r.name", roleName)
                .and(StringUtils.isNotBlank(keyword), wrapper ->
                        wrapper.like("u.name", keyword)
                                .or().
                                like("u.email", keyword)
                                .or().
                                like("u.phone", keyword))
                .orderByDesc("u.created_time");

        PageDTO<UserWithRole> pageQueryForMybatis = new PageDTO<>(pageQuery.getPageIndex(), pageQuery.getPageSize());
        PageDTO<UserWithRole> userWithRolePage = enterpriseUserRoleMapper.pageUserWithRole(pageQueryForMybatis, queryWrapper);
        return new PageResult<>(userWithRolePage.getTotal(), userWithRolePage.getRecords());
    }
}