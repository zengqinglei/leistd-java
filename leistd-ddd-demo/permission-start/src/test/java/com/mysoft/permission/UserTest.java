package com.mysoft.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mysoft.leistd.domain.repositories.HasKeyRepository;
import com.mysoft.permission.user.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    HasKeyRepository<User, String> userRepository;

    @Test
    public void testUserAssignToAdmin() {
        LambdaQueryWrapper<User> userQuery = new LambdaQueryWrapper<User>()
                .eq(User::getUsername, "zengql");
        User user = userRepository.getOne(userQuery);
        user.admin();
        boolean success = userRepository.updateById(user);
        Assert.assertTrue(success);
    }
}
