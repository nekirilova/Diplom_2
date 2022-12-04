package ru.practikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserLoginTest {
private UserGenerator userGenerator;
private LoginUser loginUser;
private UserClient userClient;
private User user;
private String token;
private ValidatableResponse response;

@Before //перед каждым тестом создаем пользователя с корректными данными
    public void setUp() {
    userGenerator = new UserGenerator();
    userClient = new UserClient();
    user = userGenerator.getCorrectUserData();//создаем объект класса с правильными данными
    response = userClient.create(user);//создаем пользователя через API
}

@After//после каждого теста удаляем созданного пользователя
    public void cleanUp() {
    token = userClient.getToken(response);//получаем токен авторизации
    userClient.delete(token);//удаляем пользователя по этому токену
}

@Test
    @DisplayName("авторизация с правильными данными возвращает в ответ статус код 200")
    public void loginUserWithCorrectDataReturnsStatusCode200() {
    int expectedStatusCode = 200; //ожидаемый статус код
    loginUser = userGenerator.getCorrectLoginData(); //создаем объект для авторизации с правильными данными
    //авторизуемся, извлекаем статус код из ответа и записываем его в переменную
    int actualStatusCode = userClient.login(loginUser).extract().statusCode();
//проверяем, что фактический и ожидаемый статус код совпадают
    Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
}
    @Test
    @DisplayName("авторизация с неправильным емейлом")
    public void loginUserWithIncorrectEmailReturnsStatusCode401() {
        int expectedStatusCode = 401; //ожидаемый статус код
        loginUser = userGenerator.getIncorrectEmailLoginData(); //создаем объект для авторизации с Неправильным емейлом
        //авторизуемся, извлекаем статус код из ответа и записываем его в переменную
        int actualStatusCode = userClient.login(loginUser).extract().statusCode();
//проверяем, что фактический и ожидаемый статус код совпадают
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
    }
    @Test
    @DisplayName("авторизация с неправильным паролем")
    public void loginUserWithIncorrectPasswordReturnsStatusCode401() {
        int expectedStatusCode = 401; //ожидаемый статус код
        loginUser = userGenerator.getIncorrectPasswordLoginData(); //создаем объект для авторизации с неправильным паролем
        //авторизуемся, извлекаем статус код из ответа и записываем его в переменную
        int actualStatusCode = userClient.login(loginUser).extract().statusCode();
//проверяем, что фактический и ожидаемый статус код совпадают
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
    }

}
