package com.bbrick.auth.config.security.filter.jwt;

import com.bbrick.auth.comn.exceptions.AuthenticationException;
import com.bbrick.auth.comn.utils.JwtTokenUtil;
import com.bbrick.auth.comn.web.WebConstants;
import com.bbrick.auth.config.security.authentication.AuthenticatedAuthentication;
import com.bbrick.auth.config.security.authentication.EmailPasswordAuthentication;
import com.bbrick.auth.config.security.handler.jwt.JwtAuthenticationFailHandler;
import com.bbrick.auth.config.security.handler.jwt.JwtAuthenticationSuccessHandler;
import com.bbrick.auth.core.auth.application.TokenService;
import com.bbrick.auth.core.user.application.UserDetailService;
import com.bbrick.auth.core.user.domain.entity.UserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String JWT_TOKEN_EXCEPT_STRING = "Bearer ";
    private static final int JWT_TOKEN_STRING_START = 7;

    private final UserDetailService userDetailService;
    private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    private final JwtAuthenticationFailHandler jwtAuthenticationFailHandler;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getToken(request);
        if (accessToken != null) {
            try {
                this.checkLogout(accessToken);
                String email = jwtTokenUtil.getEmail(accessToken);
                this.validateAccessToken(accessToken);

                UserDetails userDetails = userDetailService.loadUserByUsername(email);
                this.setAuthentication(request, userDetails);

            } catch(AuthenticationException e) {
                // this.jwtAuthenticationFailHandler.handle(request, response, e);
            }

        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("X-AUTH-TOKEN");

        if (!(StringUtils.hasText(authorization) && authorization.startsWith(JWT_TOKEN_EXCEPT_STRING))) { return null; }

        return authorization.substring(JWT_TOKEN_STRING_START);
    }

    private void validateAccessToken(String accessToken) {
        if (!jwtTokenUtil.validateToken(accessToken)) {

        }
    }

    private void checkLogout(String accessToekn) {
        // Redis에서 로그아웃 체크
    }

    private void setAuthentication(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request, userDetails);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
