package com.bbrick.auth.config.security.handler.jwt;

import com.bbrick.auth.config.security.authentication.AuthenticatedAuthentication;
import com.bbrick.auth.config.security.handler.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AuthenticatedAuthentication authentication) {
        // 토큰 발행

    }


}
