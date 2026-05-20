package com.mysoft.leistd.security.impl;

import com.mysoft.leistd.security.ClaimsPrincipal;
import com.mysoft.leistd.security.CurrentPrincipalAccessor;

public abstract class BaseCurrentPrincipalAccessorImpl implements CurrentPrincipalAccessor {

    final ThreadLocal<ClaimsPrincipal> currentPrincipal = new ThreadLocal<>();

    protected abstract ClaimsPrincipal getClaimsPrincipal();

    @Override
    public ClaimsPrincipal principal() {
        ClaimsPrincipal principal = currentPrincipal.get();
        if (principal == null) {
            principal = getClaimsPrincipal();
        }
        return principal;
    }

    @Override
    public AutoCloseable change(ClaimsPrincipal principal) {
        ClaimsPrincipal parent = currentPrincipal.get();
        currentPrincipal.set(principal);
        return () -> {
            currentPrincipal.remove();
            currentPrincipal.set(parent);
        };
    }
}
