package com.bbrick.auth.acceptance.auth;

import com.bbrick.auth.acceptance.AcceptanceTest;
import com.bbrick.auth.acceptance.common.AuthToken;
import com.bbrick.auth.view.web.auth.dto.LoginRequest;
import com.bbrick.auth.view.web.user.dto.UserJoinRequest;
import com.bbrick.auth.view.web.user.dto.UserJoinResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.bbrick.auth.acceptance.auth.steps.AuthAcceptanceTestStep.*;
import static com.bbrick.auth.acceptance.user.steps.UserAcceptanceTestStep.회원_가입이_되어있다;

@DisplayName("인증 Acceptance Test")
class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("인증 기능")
    @Test
    void authTest() {
        // given
        회원_가입이_되어있다();

        LoginRequest failRequest = new LoginRequest();

        failRequest.setEmail("none@gmail.com");
        failRequest.setPassword("Passw@rd123");

        // then
        // 회원정보가_없어서_로그인_요청에_실패한다(failRequest);

        LoginRequest successRequest = new LoginRequest();

        successRequest.setEmail("test123@gmail.com");
        successRequest.setPassword("Passw@rd123");

        // when
        ExtractableResponse<Response> 로그인_성공_응답 = 로그인_요청을_한다(successRequest);
        // then
        로그인_요청에_성공한다(로그인_성공_응답);
        AuthToken 인증_토큰 = 토근정보를_반환한다(로그인_성공_응답);
        // 인증_토큰을_저장한다(인증_토큰);

        // when
        ExtractableResponse<Response> 로그아웃_성공_응답 = 로그아웃_요청을_한다(인증_토큰);

        // then
        로그아웃_요청에_성공한다(로그아웃_성공_응답);
    }
}
