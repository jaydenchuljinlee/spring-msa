package com.bbrick.auth.config.security.handler.session;

import com.bbrick.auth.comn.BaseResponse;
import com.bbrick.auth.comn.exceptions.AuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class AuthenticationFailHandler {

    public void handle(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        this.setResponse(response, exception);
    }

    private void setResponse(HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(
                new ObjectMapper().writeValueAsString(BaseResponse.fail(exception.getMessage()))
        );
    }
}
