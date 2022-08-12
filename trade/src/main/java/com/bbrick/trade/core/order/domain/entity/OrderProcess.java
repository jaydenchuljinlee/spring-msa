package com.bbrick.trade.core.order.domain.entity;

import com.bbrick.trade.comm.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity @Table @Getter
public class OrderProcess extends BaseEntity {
    @Column(name = "order_id")
    @NotBlank
    private long orderId;

    @Column(name = "quantity")
    @NotBlank
    private int quantity;

    @Column(name = "price")
    @NotBlank
    private long price;

    @Builder
    public static OrderProcess of(long orderId, int quantity, long price) {
        return OrderProcess.builder()
                .orderId(orderId)
                .quantity(quantity)
                .price(price)
                .build();
    }
}
