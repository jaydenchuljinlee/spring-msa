package com.bbrick.auth.core.auth.domain.exceptions;

import com.bbrick.auth.comn.exceptions.AuthenticationException;

public class AuthenticationFailException extends AuthenticationException {
    public AuthenticationFailException(String message) {
        super(message);
    }

    public static AuthenticationFailException userNotRegistered(String email) {
        return new AuthenticationFailException("Authentication failed - User not registered with the email: " + email);
    }

    public static AuthenticationFailException passwordNotMatched(String email) {
        return new AuthenticationFailException("Authentication failed - Password is not matched with the email:" + email);
    }
}
