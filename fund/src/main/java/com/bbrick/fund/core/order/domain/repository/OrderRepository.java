package com.bbrick.fund.core.order.domain.repository;

import com.bbrick.fund.core.order.domain.entity.Order;
import com.bbrick.fund.core.order.domain.entity.OrderStatus;
import com.bbrick.fund.core.order.domain.entity.OrderType;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(long id);

    Optional<Order> findByOrderStatusAndOrderTypeAndPriceOrderById(OrderStatus orderStatus, OrderType orderType, long price);
}
