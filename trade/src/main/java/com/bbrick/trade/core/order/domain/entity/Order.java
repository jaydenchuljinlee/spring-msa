package com.bbrick.trade.core.order.domain.entity;

import com.bbrick.trade.comm.entity.BaseEntity;
import com.bbrick.trade.core.order.domain.dto.OrderRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "`order`") @Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) @Getter
public class Order extends BaseEntity {
    @Column(name ="user_id")
    private long userId;

    @Column(name = "product_id")
    private long productId;

    @Column(name ="order_cnt")
    private int orderCnt;

    @Column(name = "remained_cnt")
    private int remainedCnt;

    @Column(name = "price")
    private long price;

    @Column(name = "order_status")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "order_type")
    @Enumerated(value = EnumType.STRING)
    private OrderType orderType;

    @Builder
    protected Order(long userId, long productId, int orderCnt, long price, int remainedCnt, OrderStatus orderStatus, OrderType orderType) {
        this.userId = userId;
        this.productId = productId;
        this.orderCnt = orderCnt;
        this.remainedCnt = remainedCnt;
        this.price = price;
        this.orderStatus = orderStatus;
        this.orderType = orderType;
    }

    public static Order convertFromOrderRequest(OrderRequest request) {
        return new OrderBuilder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .orderCnt(request.getOrderCnt())
                .remainedCnt(request.getRemainedCnt())
                .price(request.getPrice())
                .orderStatus(OrderStatus.PROCEEDING)
                .orderType(request.getOrderType())
                .build();
    }

    public void reduceQuantity(long mayReduceCnt) {
        this.remainedCnt -= mayReduceCnt;
    }

    public void setStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getCalculatedQuantity(int val) {
        return this.orderCnt < val ? 0 : this.orderCnt - val;
    }
}
