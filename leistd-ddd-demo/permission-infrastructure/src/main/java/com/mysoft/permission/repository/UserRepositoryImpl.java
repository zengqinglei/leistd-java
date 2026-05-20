package com.mysoft.permission.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mysoft.leistd.mybatis.repositories.MybatisHasKeyRepositoryImpl;
import com.mysoft.permission.user.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends MybatisHasKeyRepositoryImpl<User, String> {

    public UserRepositoryImpl(IService<User> service, BaseMapper<User> mapper) {
        super(service, mapper);
    }

}