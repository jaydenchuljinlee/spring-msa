package com.bbrick.auth.config.security.handler;

import com.bbrick.auth.config.security.authentication.AuthenticatedAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationSuccessHandler {
    void handle(HttpServletRequest request, HttpServletResponse response, AuthenticatedAuthentication authentication);
}
