package com.bbrick.auth.core.auth.application;

import com.bbrick.auth.comn.request.header.dto.RequestHeaderType;
import com.bbrick.auth.comn.utils.JwtTokenUtil;
import com.bbrick.auth.core.auth.domain.exceptions.InvalideTokenException;
import com.bbrick.auth.core.auth.domain.repository.LogoutAccessTokenRedisRepository;
import com.bbrick.auth.core.auth.domain.repository.RefreshTokenRedisRepository;
import com.bbrick.auth.core.auth.dto.LogoutAccessToken;
import com.bbrick.auth.core.auth.dto.RefreshToken;
import com.bbrick.auth.core.auth.dto.TokenDto;
import com.bbrick.auth.core.user.application.UserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {
    private JwtTokenUtil jwtTokenUtil;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public void validate(String token) {
        checkLogout(token);
        validateAccessToken(token);
    }

    public void validateAccessToken(String token) {
        if (this.jwtTokenUtil.isExpiredToken(token)) {
            throw new InvalideTokenException("Token was expired");
        }
    }

    @CacheEvict(value = "user", key = "#email")
    public LogoutAccessToken proccessLogout(String email, TokenDto tokenDto) {
        this.deleteRefreshToken(email);
        LogoutAccessToken logoutAccessToken = jwtTokenUtil.createLogoutToken(email, tokenDto.getAcccessToken());

        return this.logoutAccessToken(logoutAccessToken);
    }

    public RefreshToken saveRefreshToken(String email) {
        RefreshToken token = jwtTokenUtil.createRefreshToken(email);
        return refreshTokenRedisRepository.save(token);
    }

    public void deleteRefreshToken(String email) {
        refreshTokenRedisRepository.deleteById(email);
    }

    public LogoutAccessToken logoutAccessToken(LogoutAccessToken logoutAccessToken) {

        LogoutAccessToken token = logoutAccessTokenRedisRepository.save(logoutAccessToken);

        return token;
    }

    public RefreshToken getRefreshToken(String email) {
        Optional<RefreshToken> optional = refreshTokenRedisRepository.findById(email);

        if (optional.isEmpty()) {
           throw new InvalideTokenException("There is no refresh token");
        }

        return optional.get();
    }

    public void checkLogout(String accessToken) {
        if (logoutAccessTokenRedisRepository.existsById(accessToken)) {
            throw new InvalideTokenException("Token is already logged out");
        }
    }

    public String getEmail(String token) {
        return jwtTokenUtil.getEmail(token);
    }

    public String getToken(HttpServletRequest request, RequestHeaderType requestHeaderType) {
        return this.jwtTokenUtil.getToken(request, requestHeaderType);
    }

    public TokenDto getTokenDto(HttpServletRequest request) {
        String accessToken = this.getToken(request, RequestHeaderType.X_AUTH_ACCESS_TOKEN);
        String refreshToken = this.getToken(request, RequestHeaderType.X_AUTH_REFRESH_TOKEN);

        return TokenDto.of(accessToken, refreshToken);
    }
}
