package com.bbrick.auth.core.auth.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenDto {
    private static final int TOKEN_STRING_LOCATION = 7;

    private String acccessToken;
    private String refreshToken;

    public TokenDto(String accessToken, String refreshToken) {
        this.acccessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static TokenDto of(String accessToken, String refreshToken) {
        return new TokenDto(accessToken, refreshToken);
    }

    public String resolveAccessToken() {
        return this.acccessToken.substring(TOKEN_STRING_LOCATION);
    }
}
