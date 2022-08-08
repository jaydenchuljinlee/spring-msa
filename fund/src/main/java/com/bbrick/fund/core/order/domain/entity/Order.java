package com.bbrick.fund.core.order.domain.entity;

import com.bbrick.fund.comm.entity.BaseEntity;
import com.bbrick.fund.core.order.domain.dto.OrderRequest;
import lombok.*;

import javax.persistence.*;

@Table @Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) @Getter
public class Order extends BaseEntity {
    @Column(name ="user_id")
    private long userId;

    @Column(name = "product_id")
    private long proudctId;

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

    @Builder
    protected Order(long userId, long proudctId, int quantity, long price, OrderStatus orderStatus, OrderType orderType) {
        this.userId = userId;
        this.proudctId = proudctId;
        this.quantity = quantity;
        this.price = price;
        this.orderStatus = orderStatus;
        this.orderType = orderType;
    }

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
