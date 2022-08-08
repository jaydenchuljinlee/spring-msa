package com.bbrick.fund.core.order.domain.dto;

import com.bbrick.fund.comm.validation.annotation.EnumFormat;
import com.bbrick.fund.comm.validation.annotation.ProductIdFormat;
import com.bbrick.fund.core.order.domain.entity.OrderStatus;
import com.bbrick.fund.core.order.domain.entity.OrderType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class OrderRequest {
    @NotBlank
    private long userId;
    @NotBlank
    //@ProductIdFormat
    private long productId;
    @NotBlank
    private int quantity;
    @NotBlank
    private long price;
    @NotBlank
    @EnumFormat(enumClass = OrderType.class)
    private OrderType orderType;
}
