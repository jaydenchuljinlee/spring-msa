package com.bbrick.fund.core.order.domain.exceptions;

import com.bbrick.fund.comm.exceptions.IntegrationException;

public class OrderRepositoryIntegrationException extends IntegrationException {
    public OrderRepositoryIntegrationException(Throwable cause) { super(cause); }
}
