package com.bbrick.fund.core.order.application;

import com.bbrick.fund.config.kafka.KafkaProducer;
import com.bbrick.fund.core.order.domain.dto.OrderRequest;
import com.bbrick.fund.core.order.domain.entity.Order;
import com.bbrick.fund.core.order.domain.entity.OrderStatus;
import com.bbrick.fund.core.order.domain.entity.OrderType;
import com.bbrick.fund.core.order.infrastructure.jpa.JpaOrderRepository;
import com.bbrick.fund.core.product.application.ProductService;
import com.bbrick.fund.core.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class OrderService {
    private final JpaOrderRepository jpaOrderRepository;
    private final ProductService productService;
    private final KafkaProducer kafkaProducer;

    @Transactional
    public Order saveOrder(OrderRequest request) {
        // TODO request는 사전 검사

        Product product = productService.getProductById(request.getProductId());

        Order order = Order.convertFromOrderRequest(request);

        // TODO kafka에 순서대로 적재

        Order result = this.jpaOrderRepository.save(order);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                kafkaProducer.sendOrder(result.getId());
            }
        });

        return result;
   }

   @Transactional
   public void processPurchase(long orderId) {
        Optional<Order> optionalOrder = this.jpaOrderRepository.findById(orderId);

       if (optionalOrder.isEmpty()) {
           return;
       }

       Order order = optionalOrder.get();

       Optional<Order> optionalMatched = this.jpaOrderRepository.findByOrderStatusAndOrderTypeAndPriceOrderById(OrderStatus.PROCEEDING, OrderType.SELL, order.getPrice());

        if (optionalMatched.isPresent()) {
            Order matched = optionalMatched.get();
            matched.reducePrice(order.getPrice());
        }




    }
}
