package com.bbrick.auth.config.security.handler;

import com.bbrick.auth.comn.exceptions.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationFailHandler {
    void handle(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws Exception;
}
