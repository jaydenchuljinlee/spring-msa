package com.bbrick.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class NoneAuthFilter extends AbstractGatewayFilterFactory<NoneAuthFilter.Config> {

    public NoneAuthFilter() { super(Config.class); }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            return chain.filter(exchange);
        }));
    }

    public static class Config {}
}
