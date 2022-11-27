package ru.practikum;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends Client{

    private final String CREATE_USER = "api/auth/register";
    private final String DELETE_USER = "api/auth/user";

 public ValidatableResponse create(User user) {
     return given()
             .spec(getSpec())
             .body(user)
             .when()
             .post(CREATE_USER)
             .then();


 }

 public String getToken(ValidatableResponse response) {
    return response.extract().path("accessToken");

 }

 public ValidatableResponse delete(String token) {
        return given()
                .spec(getAuthSpec(token))
                .when()
                .delete(DELETE_USER)
                .then();
 }

}
