package ru.practikum;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends Client{

    private final String CREATE_USER = "api/auth/register";

 public ValidatableResponse create(User user) {
     return given()
             .spec(getSpec())
             .body(user)
             .when()
             .post(CREATE_USER)
             .then();


 }

}
