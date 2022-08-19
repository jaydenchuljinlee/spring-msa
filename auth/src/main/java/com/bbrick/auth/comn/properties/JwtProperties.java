package com.bbrick.auth.comn.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@RequiredArgsConstructor
@Component
public class JwtProperties {
    @Value("${config.auth.secret}")
    private String secret;

    @Value("${config.auth.token-expiration-hour}")
    private long tokenExpirationHour;
}
