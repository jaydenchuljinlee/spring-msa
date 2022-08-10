package com.bbrick.fund.core.order.infrastructure.jpa;

import com.bbrick.fund.core.order.domain.entity.Order;
import com.bbrick.fund.core.order.domain.entity.OrderStatus;
import com.bbrick.fund.core.order.domain.entity.OrderType;
import com.bbrick.fund.core.order.domain.exceptions.OrderRepositoryIntegrationException;
import com.bbrick.fund.core.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Supplier;

interface InnerOrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderStatusAndOrderTypeAndPriceOrderById(OrderStatus orderStatus, OrderType orderType, long price);
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
    public Optional<Order> findByOrderStatusAndOrderTypeAndPriceOrderById(OrderStatus orderStatus, OrderType orderType, long price) {
        return this.repository.findByOrderStatusAndOrderTypeAndPriceOrderById(orderStatus, orderType, price);
    }

    private <T> T wrapIntegrationException(Supplier<T> process) {
        try {
            return process.get();
        } catch (Exception e) {
            throw new OrderRepositoryIntegrationException(e);
        }
    }
}
