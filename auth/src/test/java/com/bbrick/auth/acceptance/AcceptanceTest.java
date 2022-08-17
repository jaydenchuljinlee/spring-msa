package com.bbrick.auth.acceptance;

import com.bbrick.auth.acceptance.common.TestSessionUtils;
import com.bbrick.auth.integration.IntegrationTest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

@Slf4j
public abstract class AcceptanceTest extends IntegrationTest {
    static {
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()
        );
    }

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void beforeEach_() {
        this.setDefaultRequestSpecificationIfNotSet();
        this.clearSession();
    }

    private void setDefaultRequestSpecificationIfNotSet() {
        if (RestAssured.requestSpecification == null) {
            RestAssured.requestSpecification = new RequestSpecBuilder()
                    .setPort(serverPort)
                    .setAccept(MediaType.APPLICATION_JSON_VALUE)
                    .setContentType(ContentType.JSON)
                    .build();

            log.info("Set Default Specification for RestAssured");
        }
    }

    private void clearSession() {
        TestSessionUtils.clearSession();
        log.info("Session cleared");
    }
}
