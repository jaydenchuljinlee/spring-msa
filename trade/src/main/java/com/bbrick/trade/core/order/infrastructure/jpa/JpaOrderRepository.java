package com.bbrick.trade.core.order.infrastructure.jpa;

import com.bbrick.trade.core.order.domain.entity.Order;
import com.bbrick.trade.core.order.domain.entity.OrderStatus;
import com.bbrick.trade.core.order.domain.entity.OrderType;
import com.bbrick.trade.core.order.domain.exceptions.OrderRepositoryIntegrationException;
import com.bbrick.trade.core.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

interface InnerOrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderStatusAndOrderTypeAndPriceOrderById(OrderStatus orderStatus, OrderType orderType, long price);
}

@Repository
@RequiredArgsConstructor
public class JpaOrderRepository implements OrderRepository {
    private final InnerOrderRepository repository;

    @Override
    public Order save(Order order) {
        return this.wrapIntegrationException(
                () -> this.repository.save(order)
        );
    }

    @Override
    public Optional<Order> findById(long id) {
        return this.repository.findById(id);
    }

    @Override
    public List<Order> findAllByOrderStatusAndOrderTypeAndPriceOrderById(OrderStatus orderStatus, OrderType orderType, long price) {
        return this.wrapIntegrationException(
                () -> this.repository.findAllByOrderStatusAndOrderTypeAndPriceOrderById(orderStatus, orderType, price)
        );
    }

    private <T> T wrapIntegrationException(Supplier<T> process) {
        try {
            return process.get();
        } catch (Exception e) {
            throw new OrderRepositoryIntegrationException(e);
        }
    }
}
