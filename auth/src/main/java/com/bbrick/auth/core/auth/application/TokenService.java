package com.bbrick.auth.core.auth.application;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {
    private JwtTokenUtil jwtTokenUtil;
    private UserDetailService userDetailService;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public boolean validateToken(String token) { return this.jwtTokenUtil.validateToken(token); }

    public Authentication getAuthentication(String token) {
        String email = jwtTokenUtil.getEmail(token);
        UserDetails userDetails = userDetailService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
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
        return logoutAccessTokenRedisRepository.save(logoutAccessToken);
    }

    public RefreshToken getRefreshToken(String email) {
        Optional<RefreshToken> optional = refreshTokenRedisRepository.findById(email);

        if (optional.isEmpty()) {
           throw new InvalideTokenException("there is no refresh token");
        }

        return optional.get();
    }
}
