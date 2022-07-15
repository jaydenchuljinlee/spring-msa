package com.bbrick.auth.config.security.authentication;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AuthenticatedAuthentication extends AbstractAuthenticationToken {
    @Getter
    private final long userId;

    public AuthenticatedAuthentication(long userId) {
        super(null);
        this.userId = userId;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
