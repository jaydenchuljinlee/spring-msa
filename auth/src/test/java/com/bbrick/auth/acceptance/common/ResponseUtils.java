package com.bbrick.auth.acceptance.common;

import com.bbrick.auth.comn.BaseResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class ResponseUtils {
    public static <T> BaseResponse<T> extractBaseResponse(ExtractableResponse<Response> response) {
        return (BaseResponse<T>) response.as(BaseResponse.class);
    }
}
