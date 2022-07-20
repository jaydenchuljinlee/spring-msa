package com.bbrick.auth.core.user.application;

import com.bbrick.auth.comn.utils.JwtTokenUtil;
import com.bbrick.auth.core.auth.application.AuthenticationService;
import com.bbrick.auth.core.auth.application.TokenService;
import com.bbrick.auth.core.auth.domain.PasswordEncoder;
import com.bbrick.auth.core.auth.domain.exceptions.InvalideTokenException;
import com.bbrick.auth.core.auth.domain.repository.LogoutAccessTokenRedisRepository;
import com.bbrick.auth.core.auth.domain.repository.RefreshTokenRedisRepository;
import com.bbrick.auth.core.auth.dto.LogoutAccessToken;
import com.bbrick.auth.core.auth.dto.RefreshToken;
import com.bbrick.auth.core.auth.dto.TokenDto;
import com.bbrick.auth.view.web.auth.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserLoginService {
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    public TokenDto login(LoginRequest loginRequest) {

        // List<String> roles = new ArrayList<>(List.of("ROLE_USER"));

        authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        String accessToken = jwtTokenUtil.createAccessToken(loginRequest.getEmail());
        RefreshToken refreshToken = tokenService.saveRefreshToken(loginRequest.getEmail());

        return TokenDto.of(accessToken, refreshToken.getRefreshToken());
    }

    public LogoutAccessToken logout(TokenDto tokenDto, String email) {
        return tokenService.proccessLogout(email, tokenDto);
    }

    public TokenDto reissue(String refreshToken) {
        String email = getEmail();
        RefreshToken savedRefreshToken = tokenService.getRefreshToken(email);

        if (!refreshToken.equals(savedRefreshToken.getRefreshToken())) {
            throw new InvalideTokenException("refresh token do not matched");
        }

        return reissueRefreshToken(refreshToken, email);
    }

    private TokenDto reissueRefreshToken(String refreshToken, String email) {
        // TODO refresh token 만료 시간이 지났으면 리프레시 토큰을 재생성 해준다

        String accessToken = jwtTokenUtil.createAccessToken(email);

        if (!jwtTokenUtil.isExpiredToken(refreshToken)) {
            RefreshToken newRefreshToken = jwtTokenUtil.createRefreshToken(email);
            return TokenDto.of(accessToken, newRefreshToken.getRefreshToken());
        }

        // TODO 지나지 않았으면 access token만 발금해준다.
        return TokenDto.of(accessToken, refreshToken);
    }

    private String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return principal.getUsername();
    }
}