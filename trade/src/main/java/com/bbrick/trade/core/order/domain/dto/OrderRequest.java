package com.bbrick.trade.core.order.domain.dto;

import com.bbrick.trade.comm.validation.annotation.EnumFormat;
import com.bbrick.trade.core.order.domain.entity.OrderType;
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
    private int orderCnt;
    @NotBlank
    private int remainedCnt;
    @NotBlank
    private long price;
    @NotBlank
    @EnumFormat(enumClass = OrderType.class)
    private OrderType orderType;
}
