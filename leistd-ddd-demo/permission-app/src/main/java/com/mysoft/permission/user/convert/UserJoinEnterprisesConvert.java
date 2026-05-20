package com.mysoft.permission.user.convert;

import com.mysoft.permission.user.dto.UserJoinEnterprisesResDTO;
import com.mysoft.permission.user.entity.User;
import com.mysoft.permission.user.valueobject.EnterpriseUserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserJoinEnterprisesConvert {
    UserJoinEnterprisesConvert INSTANCE = Mappers.getMapper(UserJoinEnterprisesConvert.class);

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.name", target = "name")
    UserJoinEnterprisesResDTO toResDTO(User user, List<EnterpriseUserRole> joinEnterpriseRoles);
}
