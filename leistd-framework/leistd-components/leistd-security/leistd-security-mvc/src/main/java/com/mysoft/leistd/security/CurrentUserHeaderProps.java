package com.mysoft.leistd.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("leistd.current-user-header")
public class CurrentUserHeaderProps {
    /**
     * 是否启从请求头获取用户信息
     */
    private boolean enable = true;

    /**
     * 用户Id（默认：X-User-Id）
     */
    private String userId = "X-User-Id";

    /**
     * 用户名（默认：X-User-Username）
     */
    private String username = "X-User-Username";

    /**
     * 用户姓名（默认：X-User-Name）
     */
    private String name = "X-User-Name";

    /**
     * 电话号码（默认：X-User-PhoneNumber）
     */
    private String phoneNumber = "X-User-PhoneNumber";

    /**
     * 邮箱（默认：X-User-Email）
     */
    private String email = "X-User-Email";
}
