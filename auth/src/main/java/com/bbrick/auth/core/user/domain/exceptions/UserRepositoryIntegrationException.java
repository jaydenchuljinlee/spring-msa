package com.bbrick.auth.core.user.domain.exceptions;

import com.bbrick.auth.comn.exceptions.IntegrationException;

public class UserRepositoryIntegrationException extends IntegrationException {
    public UserRepositoryIntegrationException(Throwable cause) {
        super(cause);
    }
}
