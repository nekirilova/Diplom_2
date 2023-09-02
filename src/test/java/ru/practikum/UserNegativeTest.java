package ru.practikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserNegativeTest {
    private UserClient userClient;
    private UserGenerator userGenerator;
    private User user;
    ValidatableResponse response;
    //перед каждым тестом создаем объекты нужных классов
    @Before
    public void setUp() {
        userGenerator = new UserGenerator();
        userClient = new UserClient();
    }

    @Test
    @DisplayName("при создании пользователя без емейла возвращается статус код 403")
    public void createUserWithoutEmailReturnsStatusCode403() {
        int expectedStatusCode = 403;//ожидаемый статус код
        user = userGenerator.getUserWithoutEmail(); //создаем объект без емейла
        response = userClient.create(user);//создаем пользователя через API
        int actualStatusCode = response.extract().statusCode();//извлекаем из ответа статус код
//проверяем, что фактический и ожидаемый статус код совпадают
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
    }
    //при создании пользователя без пароля возвращается статус код 403
    @Test
    @DisplayName("при создании пользователя без пароля возвращается статус код 403")
    public void createUserWithoutPasswordReturnsStatusCode403() {
        int expectedStatusCode = 403;//ожидаемый статус код
        user = userGenerator.getUserWithoutPassword();//создаем объект без пароля
        response = userClient.create(user);//создаем пользователя через API
        int actualStatusCode = response.extract().statusCode();//извлекаем из ответа статус код
//проверяем, что фактический и ожидаемый статус код совпадают
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("при создании пользователя без имени возвращается статус код 403")
    public void createUserWithoutNameReturnsStatusCode403() {
        int expectedStatusCode = 403;//ожидаемый статус код
        user = userGenerator.getUserWithoutName();//создаем объект без имениы
        response = userClient.create(user);//создаем пользователя через API
        int actualStatusCode = response.extract().statusCode();//извлекаем из ответа статус код
//проверяем, что фактический и ожидаемый статус код совпадают
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
    }

}
