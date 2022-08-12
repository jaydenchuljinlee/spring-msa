package com.bbrick.trade.core.order.application;

import com.bbrick.trade.core.order.domain.entity.OrderProcess;
import com.bbrick.trade.core.order.infrastructure.jpa.JpaOrderProcessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderProcessService {
    JpaOrderProcessRepository jpaOrderProcessRepository;

    @Transactional
    public void recordOrderProcessList(List<OrderProcess> orderProcessList) {
        for (OrderProcess process: orderProcessList) {
            recordOrderProcess(process);
        }
    }

    public void recordOrderProcess(OrderProcess orderProcess) {
        jpaOrderProcessRepository.save(orderProcess);
    }
}
