package com.bbrick.auth.acceptance.common;

import io.restassured.RestAssured;

public class TestSessionUtils {

    public static void setSession(AuthToken authToken) {
        RestAssured.requestSpecification =
                RestAssured.requestSpecification.sessionId(authToken.getToken());
    }

    public static void clearSession() {
        RestAssured.requestSpecification =
                RestAssured.requestSpecification.sessionId("");
    }
}
