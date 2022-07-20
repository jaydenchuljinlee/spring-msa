package com.bbrick.auth.core.auth.domain.exceptions;

import com.bbrick.auth.comn.exceptions.IntegrationException;

public class RefreshTokenRedisRepositoryIntegrationException extends IntegrationException {
    public RefreshTokenRedisRepositoryIntegrationException(Throwable cause) {
        super(cause);
    }
}
