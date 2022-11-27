package ru.practikum;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
//тесты на создание пользователя
public class UserTest {

    private UserClient userClient;
    private UserGenerator userGenerator;
    private User user;
    private String token;
    private ValidatableResponse response;

    //перед каждым тестом создаем объекты нужных классов
    @Before
    public void setUp() {
        userGenerator = new UserGenerator();
        userClient = new UserClient();
    }
//после каждого теста удаляем созданного пользователя
    @After
    public void cleanUp() {
       token = userClient.getToken(response);//получаем токен авторизации
       userClient.delete(token);//удаляем пользователя с помощью токена
    }

//тест на создание пользователя с корректными данными
    @Test
    public void userCanBeCreated() {
        int expectedStatusCode = 200; //ожидаемый статус код
        user = userGenerator.getCorrectUserData(); //создаем объект с корректными данными
        response = userClient.create(user); //создаем пользователя через API
        int actualStatusCode = response.extract().statusCode(); //извлекаем из ответа статус код

        //проверяем, что фактический и ожидаемый статус код совпадают
        Assert.assertEquals("Incorrect status code",expectedStatusCode, actualStatusCode);
    }
//тест на создание двух одинаковых пользователей
    @Test
    public void createSameUserReturnsStatusCode403() {
        int expectedStatusCode = 403;//ожидаемый статус код
        user = userGenerator.getCorrectUserData();//создаем объект с корректными данными
        response = userClient.create(user);//создаем пользователя через API
        ValidatableResponse badResponse = userClient.create(user);//создаем пользователя еще раз с теми же данными
        int actualStatusCode = badResponse.extract().statusCode();//извлекаем из ответа статус код

        //проверяем, что фактический и ожидаемый статус код совпадают
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
    }


}
