package com.bbrick.auth.config.security.filter;

import com.bbrick.auth.comn.exceptions.AuthenticationException;
import com.bbrick.auth.comn.web.WebConstants;
import com.bbrick.auth.config.security.authentication.AuthenticatedAuthentication;
import com.bbrick.auth.config.security.authentication.EmailPasswordAuthentication;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractAuthenticationFilter extends OncePerRequestFilter {
    private final RequestMatcher requestMatcher;
    private final AuthenticationManager authenticationManager;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!this.requiresAuthentication(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            AuthenticatedAuthentication authentication = this.attemptAuthentication(request);

            // 이를 구현한 하위 객체에서 인가에 대한 처리를 한다.
            this.handleAuthenticatedSuccess(request, response, authentication);
        } catch(AuthenticationException e) {
            // 이를 구현한 하위 객체에서 인가에 대한 처리를 한다.
            this.handleAuthenticatedFail(request, response, e);
        }
    }

    private boolean requiresAuthentication(HttpServletRequest request) {
        return this.requestMatcher.matches(request);
    }

    private AuthenticatedAuthentication attemptAuthentication(HttpServletRequest request) {
        Map<String, String> requestBody = this.achieveJSONRequestBody(request);
        log.debug("Authentication request: {}", requestBody);

        if (!this.isEmailPasswordAuthenticationRequest(requestBody)) {
            throw new AuthenticationException("Fail to achieve Authentication from request");
        }

        String email = this.getRequestEmail(requestBody);
        String password = this.getRequestPassword(requestBody);
        EmailPasswordAuthentication emailPasswordAuthentication = new EmailPasswordAuthentication(email, password);

        return (AuthenticatedAuthentication) this.authenticationManager.authenticate(emailPasswordAuthentication);
    }

    private Map<String, String> achieveJSONRequestBody(HttpServletRequest request) {
        try {
            String requestBody = request.getReader().lines().collect(joining(System.lineSeparator()));

            return new ObjectMapper().readValue(requestBody, Map.class);
        } catch(Exception e) {
            throw new AuthenticationException("Fail to achieve JSON request body", e);
        }
    }

    private boolean isEmailPasswordAuthenticationRequest(Map<String, String> requestBody) {
        String email = this.getRequestEmail(requestBody);
        String password = this.getRequestPassword(requestBody);

        return StringUtils.isNotBlank(email) && StringUtils.isNoneBlank(password);
    }

    private String getRequestEmail(Map<String, String> requestBody) {
        return requestBody.get(WebConstants.RequestParameter.LOGIN_EMAIL);
    }

    private String getRequestPassword(Map<String, String> requestBody) {
        return requestBody.get(WebConstants.RequestParameter.LOGIN_PASSWORD);
    }

    protected abstract void handleAuthenticatedSuccess(HttpServletRequest request, HttpServletResponse response, AuthenticatedAuthentication authentication);

    protected abstract void handleAuthenticatedFail(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws Exception;
}
