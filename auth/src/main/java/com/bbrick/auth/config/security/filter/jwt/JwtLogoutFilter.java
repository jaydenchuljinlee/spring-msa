package com.bbrick.auth.config.security.filter.jwt;

import com.bbrick.auth.comn.exceptions.AuthenticationException;
import com.bbrick.auth.comn.request.header.dto.RequestHeaderType;
import com.bbrick.auth.comn.utils.JwtTokenUtil;
import com.bbrick.auth.core.auth.application.TokenService;
import com.bbrick.auth.core.auth.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtLogoutFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String accessToken = jwtTokenUtil.getToken(request, RequestHeaderType.X_AUTH_ACCESS_TOKEN);
            String refreshToken = jwtTokenUtil.getToken(request, RequestHeaderType.X_AUTH_REFRESH_TOKEN);

            String email = jwtTokenUtil.getEmail(accessToken);

            TokenDto tokenDto = TokenDto.of(accessToken, refreshToken);

            tokenService.proccessLogout(email, tokenDto);
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e);
        }

        filterChain.doFilter(request,response);
    }


}
