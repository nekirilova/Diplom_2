package ru.practikum;

import io.qameta.allure.junit4.DisplayName;
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
    @DisplayName("успешное создание заказа с авторизацией")
    public void createOrderWithTokenIsSuccessful() {
        int expectedStatusCode = 200; //ожидаемый статус код
        createOrderResponse = orderClient.create(order, token); //создаем заказ с авторизацией
        int actualStatusCode = createOrderResponse.extract().statusCode(); //извлекаем фактический статус код
        int orderNumber = createOrderResponse.extract().path("order.number"); //извлекаем номер заказа
        String orderId = createOrderResponse.extract().path("order._id"); //извлекаем id заказа
        //проверяем, что фактический статус код соответствует ожидаемому
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
        Assert.assertNotNull(orderNumber); //проверяем, что есть номер заказа
        Assert.assertNotNull(orderId);// проверяем, что есть айди заказа
    }

    @Test
    @DisplayName("успешное создание заказа с авторизацией возвращает имя пользователя и емейл")
    public void createOrderWithTokenReturnsUsersName() {
        String expectedUserName = user.getName(); //ожидаемое имя
        String expectedUserEmail = user.getEmail();//ожидаемый емейл
        createOrderResponse = orderClient.create(order, token); //создаем заказ с авторизацией
        String actualUserName = createOrderResponse.extract().path("order.owner.name"); //извлекаем имя пользователя
        String actualUserEmail = createOrderResponse.extract().path("order.owner.email"); //извлекаем емейл пользователя
        //проверяем, что ФР соответствует ОР
        Assert.assertEquals("Incorrect name", expectedUserName, actualUserName);
        Assert.assertEquals("Incorrect email", expectedUserEmail, actualUserEmail);

    }

    @Test
    @DisplayName("успешное создание заказа с игредиентами")
    public void createOrderWithIngredientsIsSuccessful() {
        int expectedStatusCode = 200; //ожидаемый статус код
        createOrderResponse = orderClient.create(order, token); //создаем заказ с авторизацией
        int actualStatusCode = createOrderResponse.extract().statusCode(); //извлекаем фактический статус код
        List<String> orderIngredients = createOrderResponse.extract()
                .body().jsonPath().getList("order.ingredients._id"); //извлекаем хеш номера ингредиентов

        //проверяем, что фактический статус код соответствует ожидаемому
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
        //проверяем, что список ингредиентов, полученный из ответа такой же, как переданный список индгредиентов
        Assert.assertTrue("Incorrect list of ingredients", orderIngredients.containsAll(randomIngredients));
    }


    @Test
    @DisplayName("создание заказа без авторизации возвращает статус код 401")
    public void createOrderWithoutTokenReturnsStatusCode401() {
        int expectedStatusCode = 401; //ожидаемый статус код
        String expectedMessage = "You should be authorised";
        createOrderResponse = orderClient.createWithoutToken(order); //создаем заказ без авторизации
        int actualStatusCode = createOrderResponse.extract().statusCode(); //извлекаем фактический статус код
        String actualMessage = createOrderResponse.extract().path("message"); //извлекаем сообщение об ошибке
        //проверяем, что фактический статус код соответствует ожидаемому
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
        Assert.assertEquals("Incorrect message", expectedMessage, actualMessage);
    }
}
