package com.bbrick.auth.core.auth.domain.exceptions;

import com.bbrick.auth.comn.exceptions.IntegrationException;

public class LogoutAccessTokenRedisRepositoryIntegrationException extends IntegrationException {
    public LogoutAccessTokenRedisRepositoryIntegrationException(Throwable cause) {
        super(cause);
    }
}
