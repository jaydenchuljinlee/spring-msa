package com.bbrick.auth.config.security.handler;

import com.bbrick.auth.config.security.authentication.AuthenticatedAuthentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationSuccessHandler {
    void handle(HttpServletRequest request, HttpServletResponse response, AuthenticatedAuthentication authentication);
}
