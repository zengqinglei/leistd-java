package com.mysoft.leistd.security.impl;

import com.mysoft.leistd.security.ClaimsPrincipal;

public class DefaultCurrentPrincipalAccessorImpl extends BaseCurrentPrincipalAccessorImpl {
    @Override
    protected ClaimsPrincipal getClaimsPrincipal() {
        return null;
    }
}
