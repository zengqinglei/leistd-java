package com.mysoft.permission.user.convert;

import com.mysoft.leistd.dto.convert.BaseResDTOConvert;
import com.mysoft.permission.user.dto.AdminUserResDTO;
import com.mysoft.permission.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdminUserConvert extends BaseResDTOConvert<User, AdminUserResDTO> {
    AdminUserConvert INSTANCE = Mappers.getMapper(AdminUserConvert.class);
}
