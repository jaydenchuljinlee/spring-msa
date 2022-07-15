package com.bbrick.auth.config.security.filter;

import com.bbrick.auth.comn.BaseResponse;
import com.bbrick.auth.comn.web.session.SessionUtils;
import com.bbrick.auth.config.security.authentication.AuthenticatedAuthentication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationSuccessHandler {
    public void handle(HttpServletRequest request, HttpServletResponse response, AuthenticatedAuthentication authentication) throws IOException {

    }

    private void setSecurityContextAuthentication(AuthenticatedAuthentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void setSession(HttpServletRequest request, AuthenticatedAuthentication authentication) {
        HttpSession session = request.getSession(true);
        SessionUtils.setAuthenticatedAuthentication(session, authentication);
    }

    private void setResponse(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().println(
                new ObjectMapper().writeValueAsString(BaseResponse.success())
        );
    }
}
