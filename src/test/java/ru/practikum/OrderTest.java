package ru.practikum;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class OrderTest {

    Order order;
    OrderClient orderClient;
    List<String> ingredients;
    List<String> randomIngredients;
    private UserGenerator userGenerator;
    private LoginUser loginUser;
    private UserClient userClient;
    private User user;
    private ValidatableResponse response;
    private ValidatableResponse createOrderResponse;
    private String token;

    @Before
    public void setUp() {

        orderClient = new OrderClient();
        ingredients = orderClient.getAllIngredients(); //получаем список ингридиентов для заказа
        randomIngredients = orderClient.getRandomIngredients();//составляем рандомный список из 3 ингридентов
        order = new Order(randomIngredients);
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
    public void createOrderWithTokenIsSuccessful() {
        int expectedStatusCode = 200;
        createOrderResponse = orderClient.create(order, token); //создаем заказ с авторизацией
        int actualStatusCode = createOrderResponse.extract().statusCode();
        int orderNumber = createOrderResponse.extract().path("order.number");
        System.out.println(orderNumber);
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
        Assert.assertNotNull(orderNumber);
    }
}
