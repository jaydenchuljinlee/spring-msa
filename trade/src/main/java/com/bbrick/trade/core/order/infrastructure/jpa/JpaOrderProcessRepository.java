package com.bbrick.trade.core.order.infrastructure.jpa;

import com.bbrick.trade.core.order.domain.entity.OrderProcess;
import com.bbrick.trade.core.order.domain.exceptions.OrderProcessRepositoryIntegrationException;
import com.bbrick.trade.core.order.domain.repository.OrderProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

interface InnerOrderProcessRepository extends JpaRepository<OrderProcess, Long> {}

@Repository
@RequiredArgsConstructor
public class JpaOrderProcessRepository implements OrderProcessRepository {
    private final InnerOrderProcessRepository repository;

    @Override
    public OrderProcess save(OrderProcess orderProcess) {
        return this.wrapIntegrationException(
                () -> this.repository.save(orderProcess)
        );
    }

    private <T> T wrapIntegrationException(Supplier<T> process) {
        try {
            return process.get();
        } catch(Exception e) {
            throw new OrderProcessRepositoryIntegrationException(e);
        }
    }
}
