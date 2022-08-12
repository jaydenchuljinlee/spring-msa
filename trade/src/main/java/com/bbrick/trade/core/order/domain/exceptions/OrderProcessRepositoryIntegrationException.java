package com.bbrick.trade.core.order.domain.exceptions;

import com.bbrick.trade.comm.exceptions.IntegrationException;

public class OrderProcessRepositoryIntegrationException extends IntegrationException {
    public OrderProcessRepositoryIntegrationException(Throwable casue) {
        super(casue);
    }
}
