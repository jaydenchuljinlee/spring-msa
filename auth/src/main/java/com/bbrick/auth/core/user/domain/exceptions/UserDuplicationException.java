package com.bbrick.auth.core.user.domain.exceptions;

import com.bbrick.auth.comn.exceptions.ResourceDuplicationException;

public class UserDuplicationException extends ResourceDuplicationException {
    private UserDuplicationException(String message) {
        super(message);
    }

    public static UserDuplicationException duplicatedEmail(String email) {
        return new UserDuplicationException("User email is duplicated: " + email);
    }
}
