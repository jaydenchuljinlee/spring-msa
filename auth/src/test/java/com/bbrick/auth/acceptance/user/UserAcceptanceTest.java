package com.bbrick.auth.acceptance.user;

import com.bbrick.auth.acceptance.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.bbrick.auth.acceptance.user.steps.UserAcceptanceTestStep.회원_가입_요청을_한다;
import static com.bbrick.auth.acceptance.user.steps.UserAcceptanceTestStep.회원_가입_요청이_성공한다;

@DisplayName("회원 Acceptance Test")
class UserAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원 가입 기능")
    @Test
    void userJoinTest() {
        // when
        ExtractableResponse<Response> 회원_가입_응답 = 회원_가입_요청을_한다();
        // then
        회원_가입_요청이_성공한다(회원_가입_응답);
    }
}
