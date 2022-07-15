package com.bbrick.auth.config.security.provider;

import com.bbrick.auth.config.security.authentication.AuthenticatedAuthentication;
import com.bbrick.auth.config.security.authentication.EmailPasswordAuthentication;
import com.bbrick.auth.core.auth.application.AuthenticationService;
import com.bbrick.auth.core.user.domain.entity.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class EmailPasswordAuthenticationProvider implements AuthenticationProvider {
    private final AuthenticationService authenticationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailPasswordAuthentication emailPasswordAuthentication = (EmailPasswordAuthentication) authentication;

        UserDetail authenticatedUser = this.authenticationService.authenticate(
                emailPasswordAuthentication.getEmail(),
                emailPasswordAuthentication.getPassword()
        );

        return new AuthenticatedAuthentication(authenticatedUser.getId());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(EmailPasswordAuthentication.class);
    }
}
