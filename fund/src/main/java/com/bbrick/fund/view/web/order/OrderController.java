package com.bbrick.fund.view.web.order;

import com.bbrick.fund.comm.BaseResponse;
import com.bbrick.fund.comm.web.constants.OrderConstants;
import com.bbrick.fund.core.order.application.OrderService;
import com.bbrick.fund.core.order.domain.dto.OrderRequest;
import com.bbrick.fund.core.order.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @PostMapping(OrderConstants.URL.ORDER_REQUEST_PURCHASE)
    public ResponseEntity<BaseResponse<Order>> purchase(@RequestBody OrderRequest order) {

        Order result = orderService.saveOrder(order);

        return ResponseEntity
                .ok()
                .body(BaseResponse.success(result));
    }
}
