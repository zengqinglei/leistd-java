package com.mysoft.leistd.security;

import com.mysoft.leistd.security.client.DemoClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * 分布式锁单元测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FeignUserHeaderTest {
    @Autowired
    private CurrentPrincipalAccessor currentPrincipalAccessor;
    @Autowired
    private DemoClient demoClient;

    /**
     * 测试请求客户端传递用户信息
     */
    @Test
    public void testAddUserHeader() {
        String username = "zhangs";
        Map<String, Object> claims = new HashMap<>();
        claims.put(ClaimTypes.UserId, "4a2c0e8b-019b-419a-b730-96e37334783b");
        claims.put(ClaimTypes.UserName, username);
        claims.put(ClaimTypes.Name, "张三");
        claims.put(ClaimTypes.PhoneNumber, "15827108888");
        claims.put(ClaimTypes.Email, "15827108888@qq.com");
        ClaimsPrincipal currentPrincipal = new ClaimsPrincipal(claims);
        try (AutoCloseable ignore = currentPrincipalAccessor.change(currentPrincipal)) {
            ResponseEntity<String> response = demoClient.get();
            Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
        } catch (Exception ex) {
            Assert.fail("用户信息设置失败：" + ex.getMessage());
        }
    }
}
