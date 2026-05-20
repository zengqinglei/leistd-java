package com.mysoft.leistd.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mysoft.leistd.jackson.JsonHelper;
import com.mysoft.leistd.security.impl.DefaultCurrentPrincipalAccessorImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HttpContextCurrentPrincipalAccessor extends DefaultCurrentPrincipalAccessorImpl {

    @Override
    protected ClaimsPrincipal getClaimsPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken)) {
            return super.getClaimsPrincipal();
        }
        Map<String, Object> oldClaims = ((JwtAuthenticationToken) authentication).getTokenAttributes();
        Map<String, Object> newClaims = new HashMap<>();
        Object clientId = oldClaims.get(ClaimTypes.ClientId);
        if (clientId != null) {
            newClaims.put(ClaimTypes.ClientId, clientId);
        }
        Object scope = oldClaims.get(ClaimTypes.Scope);
        if (scope != null) {
            newClaims.put(ClaimTypes.Scope, scope);
        }
        Object id = oldClaims.get("sub");
        if (id != null) {
            newClaims.put(ClaimTypes.UserId, id);
        }
        Object username = oldClaims.get("name");
        if (username != null) {
            newClaims.put(ClaimTypes.Username, username);
        }
        Object email = oldClaims.get("email");
        if (email != null) {
            newClaims.put(ClaimTypes.Email, email);
        }
        Object issueAt = oldClaims.get("auth_time");
        if (issueAt != null) {
            newClaims.put(ClaimTypes.IssuedAt, issueAt);
        }
        Object notBefore = oldClaims.get(ClaimTypes.NotBefore);
        if (notBefore != null) {
            newClaims.put(ClaimTypes.NotBefore, notBefore);
        }
        Object expiration = oldClaims.get(ClaimTypes.Expiration);
        if (expiration != null) {
            newClaims.put(ClaimTypes.Expiration, expiration);
        }
        Object userInfoStr = oldClaims.get("user");
        if (userInfoStr != null) {
            Map<String, Object> userInfo = JsonHelper.parseObject(
                    userInfoStr.toString(),
                    new TypeReference<>() {
                    });
            Object givenName = userInfo.get("Name");
            if (givenName != null) {
                newClaims.put(ClaimTypes.Name, givenName);
            }
            Object phone = userInfo.get("Phone");
            if (phone != null) {
                newClaims.put(ClaimTypes.PhoneNumber, phone);
            }
        }
        return new ClaimsPrincipal(newClaims);
    }

}
