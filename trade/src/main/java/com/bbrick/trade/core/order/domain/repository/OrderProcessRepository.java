package com.bbrick.trade.core.order.domain.repository;

import com.bbrick.trade.core.order.domain.entity.OrderProcess;

public interface OrderProcessRepository {
    OrderProcess save(OrderProcess orderProcess);
}
