package com.mysoft.leistd.security.service;

import com.mysoft.leistd.security.CurrentUser;
import com.mysoft.leistd.security.entity.User;
import com.mysoft.leistd.security.interceptor.SetCurrentUser;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobDemoService {
    final CurrentUser currentUser;

    @SetCurrentUser(username = "#{user.username}")
    public void execute(String userId, User user) {
        Assert.assertEquals(userId, currentUser.getId());
        Assert.assertEquals(user.getUsername(), currentUser.getUsername());
        Assert.assertNull(currentUser.getName());
    }
}
