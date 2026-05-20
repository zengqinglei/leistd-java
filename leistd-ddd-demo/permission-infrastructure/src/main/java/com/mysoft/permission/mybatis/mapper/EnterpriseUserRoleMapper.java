package com.mysoft.permission.mybatis.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.mysoft.permission.user.model.UserWithRole;
import com.mysoft.permission.user.valueobject.EnterpriseUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EnterpriseUserRoleMapper extends BaseMapper<EnterpriseUserRole> {

    @Select("select u.id,u.name,u.username,u.email,u.phone,u.created_time,u.creator_name,u.is_enabled,r.id as 'roleId',r.name as 'roleName' " +
            "from user u " +
            "left join enterprise_user_role eur on u.id = eur.user_id and eur.is_deleted=0 " +
            "left join role r on eur.role_id = r.id and eur.enterprise_id = r.enterprise_id ${ew.customSqlSegment}")
    PageDTO<UserWithRole> pageUserWithRole(PageDTO page, @Param(Constants.WRAPPER) Wrapper<?> wrapper);

}


