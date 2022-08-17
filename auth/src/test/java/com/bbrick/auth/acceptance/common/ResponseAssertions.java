package com.bbrick.auth.acceptance.common;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static com.bbrick.auth.acceptance.common.ResponseUtils.extractBaseResponse;
import static org.assertj.core.api.Assertions.assertThat;


public class ResponseAssertions {
    public static void assertStatusCode(ExtractableResponse<Response> response, HttpStatus status) {
        assertThat(response.statusCode()).isEqualTo(status.value());
    }

    public static void assertStatusCodeNotEquals(ExtractableResponse<Response> response, HttpStatus status) {
        assertThat(response.statusCode()).isNotEqualTo(status.value());
    }

    public static void assertResponseIsSuccess(ExtractableResponse<Response> response) {
        assertThat(extractBaseResponse(response).isSuccess()).isTrue();
    }

    public static void assertResponseIsFalse(ExtractableResponse<Response> response) {
        assertThat(extractBaseResponse(response).isSuccess()).isFalse();
    }
}
