package in.reqres.tests;

import in.reqres.models.LoginBodyLombokModel;
import in.reqres.models.LoginBodyPojoModel;
import in.reqres.models.LoginResponseLombokModel;
import in.reqres.models.LoginResponsePojoModel;
import io.qameta.allure.restassured.AllureRestAssured;
import in.reqres.helpers.CustomAllureListener;
import org.junit.jupiter.api.Test;

import static in.reqres.helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginExtendedTests {
    @Test
    void checkSuccessfulLoginWithPojoModelsTest() {

        LoginBodyPojoModel authData = new LoginBodyPojoModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponsePojoModel loginResponse = given().
                log().uri().
                log().method().
                log().body().
                contentType(JSON).
                body(authData).
                when().
                post("https://reqres.in/api/login").
                then().
                log().status().
                log().body().
                statusCode(200)
                .extract().as(LoginResponsePojoModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());

        //body("token", is("QpwL5tke4Pnpja7X4"));

    }

    @Test
    void checkSuccessfulLoginWithLombokModelsTest() {

        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel loginResponse = given().
                log().uri().
                log().method().
                log().body().
                contentType(JSON).
                body(authData).
                when().
                post("https://reqres.in/api/login").
                then().
                log().status().
                log().body().
                statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());

        //body("token", is("QpwL5tke4Pnpja7X4"));

    }


    @Test
    void checkSuccessfulLoginWithAllureTest() {

        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel loginResponse = given()
                .log().uri()
                .log().method()
                .log().body()
                .filter(new AllureRestAssured())
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());

        //body("token", is("QpwL5tke4Pnpja7X4"));

    }

    @Test
    void checkSuccessfulLoginWithCustomAllureTest() {

        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel loginResponse = given()
                .log().uri()
                .log().method()
                .log().body()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());

        //body("token", is("QpwL5tke4Pnpja7X4"));

    }

    @Test
    void checkSuccessfulLoginWithAllureStepsTest() {

        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel loginResponse = step("Make request", () ->
                given()
                        .log().uri()
                        .log().method()
                        .log().body()
                        .filter(withCustomTemplates())
                        .contentType(JSON)
                        .body(authData)
                        .when()
                        .post("https://reqres.in/api/login")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .extract().as(LoginResponseLombokModel.class));

        step("Check response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken()));

        //body("token", is("QpwL5tke4Pnpja7X4"));

    }
    @Test
    void missingPasswordTest() {
        String authData = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"\" }"; // BAD PRACTICE

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void missingEmailTest() {
        String authData = "{ \"email\": \"\", \"password\": \"cityslicka\" }"; // BAD PRACTICE

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void negative404Test() {
        String authData = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }"; // BAD PRACTICE

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void negative415Test() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(415);
    }

}
