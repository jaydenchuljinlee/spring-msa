package com.bbrick.auth.unit.utils;

import com.bbrick.auth.comn.properties.JwtProperties;
import com.bbrick.auth.comn.utils.JwtTokenUtil;
import com.bbrick.auth.core.auth.dto.LogoutAccessToken;
import com.bbrick.auth.core.auth.dto.RefreshToken;
import io.jsonwebtoken.security.WeakKeyException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@DisplayName("JwtTokenUtil 클래스")
public class JwtTokenUtilsTest {
    private JwtProperties jwtProperties;
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void beforeEach() {
        jwtProperties = new JwtProperties();
        jwtProperties.setSecret("youcantrevealthesecretkey1234012300040");
        jwtProperties.setTokenExpirationHour(1480849147370L);

        jwtTokenUtil = new JwtTokenUtil(jwtProperties);
    }

    @DisplayName("validate 메서드는")
    @Nested
    class validate {

        @DisplayName("key가 hmac SHA 알고리즘 방식이면서 jwt 포맷이면 토큰이 생성된다.")
        @Test
        void generateTokenWhenTheKeyIsProperFormat() {

            // accessToken 생성
            assertThat(jwtTokenUtil.createAccessToken("asddqe111@bbrick.com")).isInstanceOf(String.class);

            // refreshToken 생성
            assertThat(jwtTokenUtil.createRefreshToken("asddqe111@bbrick.com")).isInstanceOf(RefreshToken.class);

            // logoutToken 생성
            String accessToken = jwtTokenUtil.createAccessToken("asddqe111@bbrick.com");
            assertThat(jwtTokenUtil.createLogoutToken("asddqe111@bbrick.com", accessToken)).isInstanceOf(LogoutAccessToken.class);

        }

        @DisplayName("토큰이 jwt 포맷이 아닐 경우 false를 반환한다.")
        @Test
        void failWhenTokenIsNotFormat() {
            String accessToken = jwtTokenUtil.createAccessToken("asddqe111@bbrick.com");

            String authentication = "NotBearer " + accessToken;

            assertThat(jwtTokenUtil.getToken(authentication)).isNull();
        }

        @DisplayName("key가 hmac SHA 알고리즘 방식이 아니면 false를 반환한다.")
        @Test
        void failWhenTheKeyIsNotProper() {
            // shorter than SHA-256
            jwtProperties.setSecret("youcantrevealthesecretkey123401");

            assertThatThrownBy(() -> {
                jwtTokenUtil.getSignKey();
            }).isInstanceOf(WeakKeyException.class);
        }
    }
}
