package com.mysoft.leistd.security.impl;

import com.mysoft.leistd.security.ClaimTypes;
import com.mysoft.leistd.security.CurrentClient;
import com.mysoft.leistd.security.CurrentPrincipalAccessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CurrentClientImpl extends BaseCurrentImpl implements CurrentClient {

    public CurrentClientImpl(CurrentPrincipalAccessor currentPrincipalAccessor) {
        super(currentPrincipalAccessor);
    }

    @Override
    public boolean isAuthenticated() {
        return StringUtils.isNotBlank(getClientId());
    }

    @Override
    public String getClientId() {
        return findClaimValue(ClaimTypes.ClientId);
    }

    @Override
    public List<String> getScope() {
        return findClaimValue(ClaimTypes.Scope);
    }
}
