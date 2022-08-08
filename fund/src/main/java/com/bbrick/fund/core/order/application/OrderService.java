package com.bbrick.fund.core.order.application;

import com.bbrick.fund.core.order.domain.dto.OrderRequest;
import com.bbrick.fund.core.order.domain.entity.Order;
import com.bbrick.fund.core.order.domain.repository.OrderRepository;
import com.bbrick.fund.core.product.application.ProductService;
import com.bbrick.fund.core.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Transactional
    public Order processPurchase(OrderRequest request) {
        // TODO request는 사전 검사

        Product product = productService.getProductById(request.getProudctId());

        Order order = Order.convertFromOrderRequest(request);

        // TODO kafka에 순서대로 적재

        Order result = this.orderRepository.save(order);

        return result;
   }
}
