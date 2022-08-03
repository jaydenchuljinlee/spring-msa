package com.bbrick.fund.core.order.domain;

import com.bbrick.fund.comm.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table @Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) @Getter
public class Order extends BaseEntity {
    @Column(name ="user_id")
    private long userId;

    @Column(name = "product_id")
    private String proudct_id;

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
}
