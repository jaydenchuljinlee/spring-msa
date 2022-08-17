package com.bbrick.auth.acceptance.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthToken {
    private final String token;
}
