package com.mysoft.permission.mybatis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mysoft.permission.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
