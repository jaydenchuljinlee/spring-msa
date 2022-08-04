package com.bbrick.fund.core.order.domain.repository;

import com.bbrick.fund.core.order.domain.entity.Order;

public interface OrderRepository {
    Order save(Order order);
}
