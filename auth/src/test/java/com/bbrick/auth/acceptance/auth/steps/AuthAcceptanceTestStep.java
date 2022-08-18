package com.bbrick.auth.acceptance.auth.steps;

import com.bbrick.auth.acceptance.common.AuthToken;
import com.bbrick.auth.core.auth.domain.exceptions.AuthenticationFailException;
import com.bbrick.auth.view.web.auth.dto.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import static com.bbrick.auth.acceptance.common.ResponseAssertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class AuthAcceptanceTestStep {
    // given
    public static ExtractableResponse<Response> 로그인_되어있다(LoginRequest request) {
        ExtractableResponse<Response> 로그인_응답 = 로그인_요청을_한다(request);

        로그인_요청에_성공한다(로그인_응답);
        return 로그인_응답;
    }

    // when
    public static ExtractableResponse<Response> 로그인_요청을_한다(LoginRequest request) {
        return RestAssured.given()
                .body(request)
                .when().post("/auth/login")
                .then().extract();
    }

    // then
    public static void 로그인_요청에_성공한다(ExtractableResponse<Response> response) {
        assertStatusCode(response, HttpStatus.OK);
        assertResponseIsSuccess(response);
    }

    // then
    public static void 회원정보가_없어서_로그인_요청에_실패한다(LoginRequest request) {
        assertThatThrownBy(() -> 로그인_요청을_한다(request))
                .isInstanceOf(AuthenticationFailException.class);
    }

    public static AuthToken 토근정보를_반환한다(ExtractableResponse<Response> loginResponse) {
        String accessToken = loginResponse.cookies().get("X-AUTH-ACCESS-TOKEN");
        String refreshToken = loginResponse.cookies().get("X-AUTH-REFRESH-TOKEN");

        return new AuthToken(accessToken, refreshToken);
    }

    public static ExtractableResponse<Response> 로그아웃_요청을_한다(AuthToken authToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-AUTH-ACCESS-TOKEN", authToken.getAccessToken());
        headers.add("X-AUTH-REFRESH-TOKEN", authToken.getRefreshToken());

        return RestAssured.given()
                .headers(headers)
                .post("/auth/logout")
                .then().extract();
    }

    public static void 로그아웃_요청에_성공한다(ExtractableResponse<Response> response) {
        assertStatusCode(response, HttpStatus.FORBIDDEN);
        assertResponseIsFalse(response);
    }
}
