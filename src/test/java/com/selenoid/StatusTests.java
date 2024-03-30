package com.selenoid;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;


public class StatusTests {
    @Test
    void checkTotal() {
        given().
        when().
            get("https://selenoid.autotests.cloud/status").
        then().
            statusCode(200).
            body("total", is(20));
    }

    @Test
    void checkTotalWithLogs() {
        given().
                log().uri().
                log().method().
                log().body().
                when().
                get("https://selenoid.autotests.cloud/status").
                then().
                log().status().
                log().body().
                statusCode(200).
                body("total", is(20));
    }
}
