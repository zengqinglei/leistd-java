package com.mysoft.permission.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysoft.permission.mybatis.mapper.UserMapper;
import com.mysoft.permission.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseUserService extends ServiceImpl<UserMapper, User> implements IService<User> {

}

