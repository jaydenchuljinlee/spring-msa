package com.bbrick.trade.comm.web.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConstants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class URL {
        public static final String ORDER_REQUEST_PURCHASE = "/order/purchase";
        public static final String ORDERE_REQUEST_SALE = "/order/sale";
    }
}
