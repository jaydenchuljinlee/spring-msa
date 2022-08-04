package com.bbrick.fund.core.order.domain.entity;

import com.bbrick.fund.comm.entity.BaseEntity;
import com.bbrick.fund.core.order.domain.dto.OrderRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table @Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @Getter
public class Order extends BaseEntity {
    @Column(name ="user_id")
    private long userId;

    @Column(name = "product_id")
    private String proudctId;

    @Column(name ="quantity")
    private int quantity;

    @Column(name = "price")
    private long price;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_type")
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    public static Order convertFromOrderRequest(OrderRequest request) {
        return Order.builder()
                .userId(request.getUserId())
                .proudctId(request.getProudctId())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .orderStatus(request.getOrderStatus())
                .orderType(request.getOrderType())
                .build();
    }
}
