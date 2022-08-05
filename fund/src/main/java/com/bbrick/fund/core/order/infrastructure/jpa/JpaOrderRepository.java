package com.bbrick.fund.core.order.infrastructure.jpa;

import com.bbrick.fund.core.order.domain.entity.Order;
import com.bbrick.fund.core.order.domain.exceptions.OrderRepositoryIntegrationException;
import com.bbrick.fund.core.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

interface InnerOrderRepository extends JpaRepository<Order, Long> {}

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

    private <T> T wrapIntegrationException(Supplier<T> process) {
        try {
            return process.get();
        } catch (Exception e) {
            throw new OrderRepositoryIntegrationException(e);
        }
    }
}
