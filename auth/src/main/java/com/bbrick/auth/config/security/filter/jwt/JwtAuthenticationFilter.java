package com.bbrick.auth.config.security.filter.jwt;

import com.bbrick.auth.comn.exceptions.AuthenticationException;
import com.bbrick.auth.comn.request.header.dto.RequestHeaderType;
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
import org.springframework.security.authentication.AbstractAuthenticationToken;
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
    private final UserDetailService userDetailService;
    private final TokenService tokenService;
    private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    private final JwtAuthenticationFailHandler jwtAuthenticationFailHandler;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtTokenUtil.getToken(request, RequestHeaderType.X_AUTH_ACCESS_TOKEN);
        if (accessToken != null) {
            tokenService.validate(accessToken);

            String email = tokenService.getEmail(accessToken);

            UserDetails userDetails = userDetailService.loadUserByUsername(email);
            this.setAuthentication(request, userDetails);

        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
