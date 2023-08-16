package com.bbrick.auth.unit.core.user.application.user;

import com.bbrick.auth.comn.request.header.dto.RequestHeaderType;
import com.bbrick.auth.comn.utils.JwtTokenUtil;
import com.bbrick.auth.core.auth.application.AuthenticationService;
import com.bbrick.auth.core.auth.application.TokenService;
import com.bbrick.auth.core.auth.domain.exceptions.InvalideTokenException;
import com.bbrick.auth.core.auth.dto.LogoutAccessToken;
import com.bbrick.auth.core.auth.dto.RefreshToken;
import com.bbrick.auth.core.auth.dto.TokenDto;
import com.bbrick.auth.core.user.application.UserLoginService;
import com.bbrick.auth.view.web.auth.dto.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserLoginService 클래스")
public class UserLoginServiceTest {
    @Mock
    private JwtTokenUtil jwtTokenUtil;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private TokenService tokenService;
    @InjectMocks
    private UserLoginService userLoginService;

    @DisplayName("check 메서드는")
    @Nested
    class check {

        @DisplayName("로그인에 성공하고, 토큰을 생성한다.")
        @Test
        void successLogin() {
            // given
            LoginRequest request = new LoginRequest();
            request.setEmail("tester@gmail.com");
            request.setPassword("Passw@rd123");

            given(jwtTokenUtil.createAccessToken("tester@gmail.com")).willReturn("eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3RlckBnbWFpbC5jb20iLCJpYXQiOjE2NjEyMjEzNDIsImV4cCI6MTY2MTIyMzE0Mn0.jmHltONMguL5T7fY75RCp2qliMy47goiWxA_YtlHV2w");

            RefreshToken refreshToken = RefreshToken.createRefreshToken(
                    "tester@gmail.com",
                    "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3RlckBnbWFpbC5jb20iLCJpYXQiOjE2NjEyMjEzNDIsImV4cCI6MTY2MTgyNjE0Mn0.81Wh-2TpKJscdaCsXb2Vx_Tq2fZVj2uYN_Igh7DJBvo",
                    1000L * 60 * 60 * 24 * 7);

            given(tokenService.saveRefreshToken(request.getEmail())).willReturn(refreshToken);

            TokenDto tokenDto = TokenDto.of(
                    "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3RlckBnbWFpbC5jb20iLCJpYXQiOjE2NjEyMjEzNDIsImV4cCI6MTY2MTIyMzE0Mn0.jmHltONMguL5T7fY75RCp2qliMy47goiWxA_YtlHV2w",
                    "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3RlckBnbWFpbC5jb20iLCJpYXQiOjE2NjEyMjEzNDIsImV4cCI6MTY2MTgyNjE0Mn0.81Wh-2TpKJscdaCsXb2Vx_Tq2fZVj2uYN_Igh7DJBvo"
            );

            // when & then
            TokenDto result = userLoginService.login(request);

            assertThat(result).isInstanceOf(TokenDto.class);
            assertThat(result.getAcccessToken()).isEqualTo(tokenDto.getAcccessToken());
            assertThat(result.getRefreshToken()).isEqualTo(tokenDto.getRefreshToken());

        }

        @DisplayName("로그아웃에 성공하고 로그아웃 토큰을 발행한다.")
        @Test
        void successLogout() {
            // given
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.addHeader("X-AUTH-ACCESS-TOKEN", "123");
            request.addHeader("X-AUTH-REFRESH-TOKEN", "123");

            TokenDto tokenDto = TokenDto.of("123", "456");

            LogoutAccessToken logoutAccessToken = LogoutAccessToken.of(
                    "tester@gmail.com",
                    "123",
                    1000L * 60 * 60 * 24 * 7
            );

            given(tokenService.getToken(request, RequestHeaderType.X_AUTH_ACCESS_TOKEN)).willReturn("123");
            given(tokenService.getEmail("123")).willReturn("tester@gmail.com");
            given(tokenService.proccessLogout("tester@gmail.com", tokenDto)).willReturn(logoutAccessToken);

            LogoutAccessToken result = userLoginService.logout(request, tokenDto);

            assertThat(result).isInstanceOf(LogoutAccessToken.class);
            assertThat(result.getId()).isEqualTo(logoutAccessToken.getId());
            assertThat(result.getEmail()).isEqualTo(logoutAccessToken.getEmail());
            assertThat(result.getExpiration()).isEqualTo(logoutAccessToken.getExpiration());
        }


        @DisplayName("토큰이 유효하지 않아서 실패한다.")
        @Test
        void failedBecauseTokenIsInvalid() {
            //given
            RefreshToken refreshToken = RefreshToken.createRefreshToken("tester@gmail.com", "123", 1000L * 60 * 60 * 24 * 7);

            given(tokenService.getEmail("123")).willReturn("tester@gmail.com");
            given(tokenService.getRefreshToken("tester@gmail.com")).willReturn(
                    RefreshToken.createRefreshToken("none@gmail.com", "456", 1000L * 60 * 60 * 24 * 7)
            );

            // when & then
            assertThatThrownBy(
                    () -> userLoginService.reissue("123")
            ).isInstanceOf(InvalideTokenException.class);
        }

        @DisplayName("토큰 유효시간이 지나지 않아서 기존의 Refresh 토큰을 반환한다.")
        @Test
        void notPassedTokenExpiration() {
            // given
            given(tokenService.getEmail("456")).willReturn("tester@gmail.com");
            given(tokenService.getRefreshToken("tester@gmail.com")).willReturn(
                    RefreshToken.createRefreshToken("tester@gmail.com", "456", 1000L * 60 * 60 * 24 * 7)
            );
            given(jwtTokenUtil.createAccessToken("tester@gmail.com")).willReturn("123");
            given(jwtTokenUtil.isExpiredToken("456")).willReturn(true);

            // when & then
            TokenDto result = userLoginService.reissue("456");

            assertThat(result).isInstanceOf(TokenDto.class);
            assertThat(result.getAcccessToken()).isEqualTo("123");
            assertThat(result.getRefreshToken()).isEqualTo("456");
        }

        @DisplayName("토큰 유효시간이 지나서 새로운 Refresh 토큰을 반환한다.")
        @Test
        void PassedTokenExpiration() {
            // given
            given(tokenService.getEmail("456")).willReturn("tester@gmail.com");
            given(tokenService.getRefreshToken("tester@gmail.com")).willReturn(
                    RefreshToken.createRefreshToken("tester@gmail.com", "456", 1000L * 60 * 60 * 24 * 7)
            );
            given(jwtTokenUtil.createAccessToken("tester@gmail.com")).willReturn("123");
            given(jwtTokenUtil.isExpiredToken("456")).willReturn(false);
            given(jwtTokenUtil.createRefreshToken("tester@gmail.com")).willReturn(RefreshToken.createRefreshToken(
                    "tester@gmail.com",
                    "789",
                    1000L * 60 * 60 * 24 * 7
            ));

            // when & then
            TokenDto result = userLoginService.reissue("456");

            assertThat(result).isInstanceOf(TokenDto.class);
            assertThat(result.getAcccessToken()).isEqualTo("123");
            assertThat(result.getRefreshToken()).isEqualTo("789");
        }
    }

}
