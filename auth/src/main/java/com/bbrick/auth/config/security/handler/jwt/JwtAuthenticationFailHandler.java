package com.bbrick.auth.config.security.handler.jwt;

import com.bbrick.auth.comn.exceptions.AuthenticationException;
import com.bbrick.auth.config.security.handler.AuthenticationFailHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFailHandler implements AuthenticationFailHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws Exception {

    }


}
