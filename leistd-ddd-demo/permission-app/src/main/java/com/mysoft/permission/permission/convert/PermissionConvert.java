package com.mysoft.permission.permission.convert;

import com.mysoft.leistd.dto.convert.BaseResDTOConvert;
import com.mysoft.permission.permission.dto.PermissionResDTO;
import com.mysoft.permission.permission.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PermissionConvert extends BaseResDTOConvert<Permission, PermissionResDTO> {
    PermissionConvert INSTANCE = Mappers.getMapper(PermissionConvert.class);
}
