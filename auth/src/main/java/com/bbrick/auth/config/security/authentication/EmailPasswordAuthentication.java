package com.bbrick.auth.config.security.authentication;

import lombok.Getter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Getter
public class EmailPasswordAuthentication extends PreAuthenticatedAuthenticationToken {
    private final String email;
    private final String password;

    public EmailPasswordAuthentication(String email, String password) {
        super(email, password);
        this.email = email;
        this.password = password;
    }

}
