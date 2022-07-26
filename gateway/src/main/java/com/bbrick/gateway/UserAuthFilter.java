package com.bbrick.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

public class UserAuthFilter extends AbstractGatewayFilterFactory<UserAuthFilter.Config> {

    public UserAuthFilter() {
        super();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {



            return chain.filter(exchange);
        }));
    }

    public static class Config {}
}
