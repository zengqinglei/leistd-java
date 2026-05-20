package com.mysoft.leistd.security;

import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
public class ClaimsPrincipal implements Principal {

    final Map<String, Object> claims;

    /**
     * 获取用户名
     *
     * @return 返回用户名
     */
    @Override
    public String getName() {
        return getClaim(ClaimTypes.Username);
    }

    public <T> T getClaim(String claimType) {
        return (T) claims.get(claimType);
    }
}
