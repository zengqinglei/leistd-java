package com.mysoft.permission.user.convert;

import com.mysoft.leistd.dto.convert.BaseResDTOConvert;
import com.mysoft.permission.role.entity.EnterpriseRole;
import com.mysoft.permission.user.dto.EnterpriseUserRoleResDTO;
import com.mysoft.permission.user.dto.EnterpriseUserWithRoleResDTO;
import com.mysoft.permission.user.entity.User;
import com.mysoft.permission.user.model.UserWithRole;
import com.mysoft.permission.user.valueobject.EnterpriseUserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EnterpriseUserRoleConvert extends BaseResDTOConvert<UserWithRole, EnterpriseUserWithRoleResDTO> {
    EnterpriseUserRoleConvert INSTANCE = Mappers.getMapper(EnterpriseUserRoleConvert.class);

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.createdTime", target = "createdTime")
    @Mapping(source = "user.creatorName", target = "creatorName")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")
    EnterpriseUserWithRoleResDTO toResDTO(User user, EnterpriseRole role);

    EnterpriseUserRoleResDTO toResDTO(EnterpriseUserRole enterpriseUserRole);
}
