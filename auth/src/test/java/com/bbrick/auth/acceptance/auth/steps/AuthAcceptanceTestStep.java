package com.bbrick.auth.acceptance.auth.steps;

import com.bbrick.auth.view.web.auth.dto.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import static com.bbrick.auth.acceptance.common.ResponseAssertions.assertResponseIsSuccess;
import static com.bbrick.auth.acceptance.common.ResponseAssertions.assertStatusCode;

public class AuthAcceptanceTestStep {
    // given
    public static ExtractableResponse<Response> 로그인_되어있다() {
        ExtractableResponse<Response> 로그인_응답 = 로그인_요청을_한다();

        로그인_요청에_성공한다(로그인_응답);
        return 로그인_응답;
    }

    // when
    public static ExtractableResponse<Response> 로그인_요청을_한다() {

        LoginRequest request = new LoginRequest();

        request.setEmail("test123@gmail.com");
        request.setPassword("Passw@rd123");

        HttpHeaders headers = new HttpHeaders();

        headers.add("X-AUTH-ACCESS-TOKEN", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imlyb25qaW45MkBnbWFpbC5jb20iLCJpYXQiOjE2NTg0MTU0MjUsImV4cCI6MTY1OTAyMDIyNX0.03_XjahPWDrzkJ1V9YExyAnt9juuL0Psfoz2bI5GJDI");

        return RestAssured.given()
                .headers(headers)
                .body(request)
                .when().post("/login")
                .then().extract();
    }

    // then
    public static void 로그인_요청에_성공한다(ExtractableResponse<Response> response) {
        assertStatusCode(response, HttpStatus.OK);
        assertResponseIsSuccess(response);
    }
}
