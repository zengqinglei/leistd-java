package com.mysoft.leistd.security;

import com.mysoft.leistd.security.entity.User;
import com.mysoft.leistd.security.service.JobDemoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * 分布式锁单元测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SetCurrentUserTest {
    @Autowired
    private CurrentPrincipalAccessor currentPrincipalAccessor;
    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private JobDemoService jobDemoService;

    /**
     * 测试作用域设置用户信息
     */
    @Test
    public void testSetCurrentUser() {
        String username = "zhangs";
        Map<String, Object> claims = new HashMap<>();
        claims.put(ClaimTypes.UserId, "4a2c0e8b-019b-419a-b730-96e37334783b");
        claims.put(ClaimTypes.UserName, username);
        claims.put(ClaimTypes.Name, "张三");
        claims.put(ClaimTypes.PhoneNumber, "15827108888");
        claims.put(ClaimTypes.Email, "15827108888@qq.com");
        ClaimsPrincipal currentPrincipal = new ClaimsPrincipal(claims);
        try (AutoCloseable ignore = currentPrincipalAccessor.change(currentPrincipal)) {
            Assert.assertEquals(username, currentUser.getUsername());
        } catch (Exception ex) {
            Assert.fail("用户信息设置失败：" + ex.getMessage());
        }
    }

    /**
     * 测试方法拦截设置用户信息
     */
    @Test
    public void testAopSetCurrentUser() {
        jobDemoService.execute("4a2c0e8b-019b-419a-b730-96e37334783b", new User("zhangs"));
    }
}
