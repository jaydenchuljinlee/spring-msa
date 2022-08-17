package com.bbrick.auth.acceptance.user.steps;

import com.bbrick.auth.core.user.domain.entity.Gender;
import com.bbrick.auth.view.web.user.dto.UserJoinRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.bbrick.auth.acceptance.common.ResponseAssertions.assertResponseIsSuccess;
import static com.bbrick.auth.acceptance.common.ResponseAssertions.assertStatusCode;

public class UserAcceptanceTestStep {
    // given
    public static ExtractableResponse<Response> 회원_가입이_되어있다() {
        ExtractableResponse<Response> 회원_가입_응답 = 회원_가입_요청을_한다();
        회원_가입_요청이_성공한다(회원_가입_응답);
        return 회원_가입_응답;
    }

    // when
    public static ExtractableResponse<Response> 회원_가입_요청을_한다() {
        UserJoinRequest request = new UserJoinRequest();

        request.setEmail("test123@gmail.com");
        request.setPassword("Passw@rd123");
        request.setName("tester");
        request.setPhoneNumber("01012341234");
        request.setGender(Gender.MALE.name());

        return RestAssured
                .given().body(request)
                .when().post("/users")
                .then().extract();
    }

    // then
    public static void 회원_가입_요청이_성공한다(ExtractableResponse<Response> userJoinResponse) {
        assertStatusCode(userJoinResponse, HttpStatus.CREATED);
        assertResponseIsSuccess(userJoinResponse);
    }

}
