package com.mysoft.permission.role.convert;

import com.mysoft.leistd.dto.convert.BaseResDTOConvert;
import com.mysoft.permission.role.dto.EnterpriseRoleResDTO;
import com.mysoft.permission.role.entity.EnterpriseRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EnterpriseRoleConvert extends BaseResDTOConvert<EnterpriseRole, EnterpriseRoleResDTO> {
    EnterpriseRoleConvert INSTANCE = Mappers.getMapper(EnterpriseRoleConvert.class);
}
