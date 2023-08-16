package com.bbrick.auth.config.security.filter.jwt;

import com.bbrick.auth.comn.exceptions.AuthenticationException;
import com.bbrick.auth.comn.request.header.dto.RequestHeaderType;
import com.bbrick.auth.comn.utils.JwtTokenUtil;
import com.bbrick.auth.core.auth.application.TokenService;
import com.bbrick.auth.core.auth.dto.TokenDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtLogoutFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String accessTokenHeader = request.getHeader(RequestHeaderType.X_AUTH_ACCESS_TOKEN.value());
            String refreshTokenHeader = request.getHeader(RequestHeaderType.X_AUTH_REFRESH_TOKEN.value());

            String accessToken = jwtTokenUtil.getToken(accessTokenHeader);
            String refreshToken = jwtTokenUtil.getToken(refreshTokenHeader);

            String email = jwtTokenUtil.getEmail(accessToken);

            TokenDto tokenDto = TokenDto.of(accessToken, refreshToken);

            tokenService.proccessLogout(email, tokenDto);
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e);
        }

        filterChain.doFilter(request,response);
    }


}
