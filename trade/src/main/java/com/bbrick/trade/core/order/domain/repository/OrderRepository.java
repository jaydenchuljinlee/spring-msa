package com.bbrick.trade.core.order.domain.repository;

import com.bbrick.trade.core.order.domain.entity.Order;
import com.bbrick.trade.core.order.domain.entity.OrderStatus;
import com.bbrick.trade.core.order.domain.entity.OrderType;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(long id);

    List<Order> findAllByOrderStatusAndOrderTypeAndPriceOrderById(OrderStatus orderStatus, OrderType orderType, long price);
}
