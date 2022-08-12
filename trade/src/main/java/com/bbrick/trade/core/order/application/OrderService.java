package com.bbrick.trade.core.order.application;

import com.bbrick.trade.config.kafka.KafkaProducer;
import com.bbrick.trade.core.order.domain.dto.OrderRequest;
import com.bbrick.trade.core.order.domain.entity.Order;
import com.bbrick.trade.core.order.domain.entity.OrderProcess;
import com.bbrick.trade.core.order.domain.entity.OrderStatus;
import com.bbrick.trade.core.order.domain.entity.OrderType;
import com.bbrick.trade.core.order.infrastructure.jpa.JpaOrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class OrderService {
    private final JpaOrderRepository jpaOrderRepository;
    private final KafkaProducer kafkaProducer;
    private final OrderProcessService orderProcessService;

    @Transactional
    public Order saveOrder(OrderRequest request) {
        // TODO request는 사전 검사

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
        Optional<Order> optionalBuy = this.jpaOrderRepository.findById(orderId);

       if (optionalBuy.isEmpty()) {
           // TODO order exception 처리 필요
           return;
       }

       Order buy = optionalBuy.get();

       // 주문 진행 중인 상태 && 동일 가격인 주문 중에 주문 번호 기준으로 리스트 조회
       List<Order> saleList = this.jpaOrderRepository.findAllByOrderStatusAndOrderTypeAndPriceOrderById(OrderStatus.PROCEEDING, OrderType.SELL, buy.getPrice());

       if (saleList.size() < 1) { return; }

       changeConditionOfProduct(buy, saleList);

    }

    private void changeConditionOfProduct(Order buy, List<Order> saleList) {
        for (Order sale: saleList) {
            int diff = Math.max(buy.getRemainedCnt() - sale.getRemainedCnt(), 0);
            int quantity = buy.getRemainedCnt() - diff;

            buy.reduceQuantity(quantity);
            sale.reduceQuantity(quantity);

            OrderProcess buyProcess = OrderProcess.of(buy.getId(), quantity, buy.getPrice() * quantity);
            OrderProcess saleProcess = OrderProcess.of(sale.getId(), quantity, sale.getPrice() * quantity);

            this.orderProcessService.recordOrderProcess(buyProcess);
            this.orderProcessService.recordOrderProcess(saleProcess);

            if (diff > 0) {
                sale.setStatus(OrderStatus.COMPLETED);
                continue;
            }

            if (diff == 0) { sale.setStatus(OrderStatus.COMPLETED); }

            buy.setStatus(OrderStatus.COMPLETED);
            break;
        }


    }
}
