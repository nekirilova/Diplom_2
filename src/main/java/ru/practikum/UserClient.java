package ru.practikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

//класс с методами для создания, авторизации и удаления пользователя
public class UserClient extends Client{

    private final String CREATE_USER = "api/auth/register"; //ручка для создания пользователя
    private final String DELETE_USER = "api/auth/user"; //ручка для удаления пользователя
    private final String LOGIN_USER = "api/auth/login";//Ручка для авторизации пользователя

   @Step("метод для создания пользователя")
 public ValidatableResponse create(User user) {
     return given()
             .spec(getSpec())
             .body(user)
             .when()
             .post(CREATE_USER)
             .then();


 }
@Step("метод для получения токена авторизации")
 public String getToken(ValidatableResponse response) {
    return response.extract().path("accessToken");

 }
@Step("метод для удаления пользователя")
 public ValidatableResponse delete(String token) {
        return given()
                .spec(getAuthSpec(token))
                .when()
                .delete(DELETE_USER)
                .then();
 }

 @Step("метод для авторизации")
 public ValidatableResponse login(LoginUser loginUser) {
     return given()
             .spec(getSpec())
             .body(loginUser)
             .when()
             .post(LOGIN_USER)
             .then();
 }
@Step("метод для обновления данных пользователя с авторизацией")
 public ValidatableResponse editWithAuthorization(User user, String token) {
     return given()
             .spec(getAuthSpec(token))
             .body(user)
             .when()
             .patch(DELETE_USER)
             .then();
 }

  @Step("метод для обновления данных пользователя без авторизации")
    public ValidatableResponse editWithoutAuthorization(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .patch(DELETE_USER)
                .then();
    }
}
