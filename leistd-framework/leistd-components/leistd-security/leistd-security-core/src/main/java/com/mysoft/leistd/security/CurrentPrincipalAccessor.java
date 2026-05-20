package com.mysoft.leistd.security;

public interface CurrentPrincipalAccessor {
    ClaimsPrincipal principal();

    AutoCloseable change(ClaimsPrincipal principal);
}
