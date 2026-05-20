package com.mysoft.leistd.security.impl;

import com.mysoft.leistd.security.ClaimTypes;
import com.mysoft.leistd.security.CurrentPrincipalAccessor;
import com.mysoft.leistd.security.CurrentUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CurrentUserImpl extends BaseCurrentImpl implements CurrentUser {

    public CurrentUserImpl(CurrentPrincipalAccessor currentPrincipalAccessor) {
        super(currentPrincipalAccessor);
    }

    @Override
    public boolean isAuthenticated() {
        return StringUtils.isNotBlank(getId()) || StringUtils.isNotBlank(getUsername());
    }

    @Override
    public String getId() {
        return findClaimValue(ClaimTypes.UserId);
    }

    @Override
    public String getUsername() {
        return findClaimValue(ClaimTypes.Username);
    }

    @Override
    public String getName() {
        return findClaimValue(ClaimTypes.Name);
    }

    @Override
    public String getPhoneNumber() {
        return findClaimValue(ClaimTypes.PhoneNumber);
    }

    @Override
    public String getEmail() {
        return findClaimValue(ClaimTypes.Email);
    }

    @Override
    public List<String> getRoles() {
        return findClaimValue(ClaimTypes.Role);
    }

    @Override
    public boolean isInRole(String roleName) {
        return getRoles().stream().anyMatch(role -> role.equalsIgnoreCase(roleName));
    }
}
