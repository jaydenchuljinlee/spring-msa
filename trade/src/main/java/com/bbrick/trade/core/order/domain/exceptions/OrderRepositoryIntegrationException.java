package com.bbrick.trade.core.order.domain.exceptions;

import com.bbrick.trade.comm.exceptions.IntegrationException;

public class OrderRepositoryIntegrationException extends IntegrationException {
    public OrderRepositoryIntegrationException(Throwable cause) { super(cause); }
}
