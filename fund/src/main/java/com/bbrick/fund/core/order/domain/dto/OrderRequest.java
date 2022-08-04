package com.bbrick.fund.core.order.domain.dto;

import com.bbrick.fund.core.order.domain.entity.OrderStatus;
import com.bbrick.fund.core.order.domain.entity.OrderType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderRequest {
    private long userId;
    private String proudctId;
    private int quantity;
    private long price;
    private OrderStatus orderStatus;
    private OrderType orderType;
}
