package com.bbrick.auth.core.user.domain.exceptions;

import com.bbrick.auth.comn.exceptions.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(long userId) {
        super("User with the id not found: " + userId);
    }
}
