package ru.practikum;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class OrderListTest {
    Order order;
    OrderClient orderClient;
    List<String> ingredients;
    List<String> randomIngredients;
    private UserGenerator userGenerator;
    private LoginUser loginUser;
    private UserClient userClient;
    private User user;
    private ValidatableResponse response;
    private ValidatableResponse getOrderListResponse;
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
       orderClient.create(order,token);//создаем заказ
    }

    @After //после каждого теста удаляем пользователя и отменяем заказ
    public void cleanUp() {
        userClient.delete(token); //удаляем пользователя
    }

    @Test
    public void getOrderListWithAuthorizationIsSuccessful() {
        int expectedStatusCode = 200; //ожидаемый статус код
        getOrderListResponse = orderClient.getOrdersListWithAuth(token); //получаем список заказов
        int actualStatusCode = getOrderListResponse.extract().statusCode();

        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
      }

    @Test
    public void getOrderListWithoutAuthorizationReturnsStatusCode401() {
        int expectedStatusCode = 401; //ожидаемый статус код
        getOrderListResponse = orderClient.getOrdersListWithoutAuth(); //получаем список заказов
        int actualStatusCode = getOrderListResponse.extract().statusCode();

        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
    }
}
