package com.bbrick.auth.core.auth.domain.exceptions;

import com.bbrick.auth.comn.exceptions.AuthenticationException;

public class InvalideTokenException extends AuthenticationException {
    public InvalideTokenException(String message) {
        super(message);
    }
}
