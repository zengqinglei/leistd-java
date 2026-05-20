package com.mysoft.leistd.security.impl;

import com.mysoft.leistd.security.BaseCurrent;
import com.mysoft.leistd.security.ClaimTypes;
import com.mysoft.leistd.security.ClaimsPrincipal;
import com.mysoft.leistd.security.CurrentPrincipalAccessor;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Date;

@RequiredArgsConstructor
public abstract class BaseCurrentImpl implements BaseCurrent {
    protected final CurrentPrincipalAccessor currentPrincipalAccessor;

    @Override
    public Date getIssuedAt() {
        Object issuedAt = findClaimValue(ClaimTypes.IssuedAt);
        return parseClaimValueToDate(issuedAt);
    }

    @Override
    public Date getNotBefore() {
        Object notBefore = findClaimValue(ClaimTypes.NotBefore);
        return parseClaimValueToDate(notBefore);
    }

    @Override
    public Date getExpiration() {
        Object expiration = findClaimValue(ClaimTypes.Expiration);
        return parseClaimValueToDate(expiration);
    }

    private Date parseClaimValueToDate(Object claimValue) {
        if (claimValue instanceof Date) {
            return (Date) claimValue;
        } else if (claimValue instanceof Instant) {
            return Date.from((Instant) claimValue);
        } else if (claimValue instanceof Long) {
            return new Date((Long) claimValue * 1000);
        }
        return null;
    }

    protected <T> T findClaimValue(String claimType) {
        ClaimsPrincipal principal = currentPrincipalAccessor.principal();
        if (principal == null) {
            return null;
        }
        return principal.getClaim(claimType);
    }
}
