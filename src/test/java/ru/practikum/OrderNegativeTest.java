package ru.practikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderNegativeTest {

    Order order;
    OrderClient orderClient;
    private UserGenerator userGenerator;

    private UserClient userClient;
    private User user;
    private ValidatableResponse response;
    private String token;

    @Before
    public void setUp() {

        orderClient = new OrderClient();

        userGenerator = new UserGenerator();
        userClient = new UserClient();
        user = userGenerator.getCorrectUserData();//создаем объект класса с правильными данными
        response = userClient.create(user);//создаем пользователя через API
        token = userClient.getToken(response);//получаем токен авторизации
    }

    @After //после каждого теста удаляем пользователя и отменяем заказ
    public void cleanUp() {
        userClient.delete(token); //удаляем пользователя
    }

    @Test
    @DisplayName("проверяем, что создание заказа без ингридентов невозможно")
    public void createOrderWithoutIngredientsReturnsStatusCode400() {
        order = new Order();
        int expectedStatusCode = 400; //ожидаемый статус код
        response = orderClient.create(order, token); //создаем заказ с токеном, но без ингредиентов
        int actualStatusCode = response.extract().statusCode(); //извлекаем фактический статус код
        //проверяем, что фактический и ожидаемый статус код совпадают
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("Проверяем, что создание заказа с неправильным ингредиентом вернет ошибку сервера")
    public void createOrderWithWrongIngredientReturnsStatusCode500() {
        int expectedStatusCode = 500;
        order = new Order(orderClient.generateWrongIngredients());
        response = orderClient.create(order, token);
        int actualStatusCode = response.extract().statusCode();
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
    }
}
