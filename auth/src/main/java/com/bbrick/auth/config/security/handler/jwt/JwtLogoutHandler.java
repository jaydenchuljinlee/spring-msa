package com.bbrick.auth.config.security.handler.jwt;

import com.bbrick.auth.comn.exceptions.AuthenticationException;
import com.bbrick.auth.comn.request.header.dto.RequestHeaderType;
import com.bbrick.auth.comn.utils.JwtTokenUtil;
import com.bbrick.auth.core.auth.application.TokenService;
import com.bbrick.auth.core.auth.dto.LogoutAccessToken;
import com.bbrick.auth.core.auth.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {
    private final TokenService tokenService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            TokenDto tokenDto = tokenService.getTokenDto(request);
            String email = tokenService.getEmail(tokenDto.getAcccessToken());

            LogoutAccessToken logoutAccessToken = tokenService.proccessLogout(email, tokenDto);
        } catch (AuthenticationException e) {
            throw new AuthenticationException(e);
        }
    }
}
