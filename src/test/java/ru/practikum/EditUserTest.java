package ru.practikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EditUserTest {
    private UserGenerator userGenerator;

    private UserClient userClient;
    private User user;
    private String token;
    private ValidatableResponse response;
    private String expectedMessage = "You should be authorised";

    @Before //перед каждым тестом создаем пользователя с корректными данными
    public void setUp() {
        userGenerator = new UserGenerator();
        userClient = new UserClient();
        user = userGenerator.getCorrectUserData();//создаем объект класса с правильными данными
        response = userClient.create(user);//создаем пользователя через API
        token = userClient.getToken(response);//получаем токен авторизации

    }

    @After//после каждого теста удаляем созданного пользователя
    public void cleanUp() {
        userClient.delete(token);//удаляем пользователя по токену
    }

    @Test
    @DisplayName("можно обновить все поля сразу")
    public void editUserWithTokenReturnsStatusCode200() {
        int expectedStatusCode = 200; //ожидаемый статус-код
        ValidatableResponse editResponse = userClient
                .editWithAuthorization(userGenerator.getRandomUser(), token);//изменяем данные пользователя
                                                //на рандомные, используем полученный токен для авторизации
        int actualStatusCode = editResponse.extract().statusCode();//извлекаем фактический статус код
        //сравниваем фактический и ожидаемый статус-код
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);

    }

    @Test
    @DisplayName("можно обновить только поле емейл")
    public void editUserEmailWithTokenReturnsStatusCode200() {
        int expectedStatusCode = 200; //ожидаемый статус-код
        ValidatableResponse editResponse = userClient
                .editWithAuthorization(userGenerator.getRandomEmail(), token);//изменяем емейл пользователя
        //на рандомный, используем полученный токен для авторизации
        int actualStatusCode = editResponse.extract().statusCode();//извлекаем фактический статус код
        //сравниваем фактический и ожидаемый статус-код
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);

    }

    @Test
    @DisplayName("можно обновить только поле пароль")
    public void editUserPasswordWithTokenReturnsStatusCode200() {
        int expectedStatusCode = 200; //ожидаемый статус-код
        ValidatableResponse editResponse = userClient
                .editWithAuthorization(userGenerator.getRandomPassword(), token);//изменяем пароль пользователя
        //на рандомный, используем полученный токен для авторизации
        int actualStatusCode = editResponse.extract().statusCode();//извлекаем фактический статус код
        //сравниваем фактический и ожидаемый статус-код
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);

    }

    @Test
    @DisplayName("можно обновить только поле имя")
    public void editUserNameWithTokenReturnsStatusCode200() {
        int expectedStatusCode = 200; //ожидаемый статус-код
        ValidatableResponse editResponse = userClient
                .editWithAuthorization(userGenerator.getRandomName(), token);//изменяем имя пользователя
        //на рандомное, используем полученный токен для авторизации
        int actualStatusCode = editResponse.extract().statusCode();//извлекаем фактический статус код
        //сравниваем фактический и ожидаемый статус-код
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);

    }

    @Test
    @DisplayName("если обновлять поля без авторизации, вернется ошибка 401")
    public void editUserWithoutTokenReturnsStatusCode401() {
        int expectedStatusCode = 401; //ожидаемый статус-код
        ValidatableResponse editResponse = userClient
                .editWithoutAuthorization(userGenerator.getRandomUser());//изменяем данные пользователя на рандомные
        int actualStatusCode = editResponse.extract().statusCode();//извлекаем фактический статус код
        String actualMessage = editResponse.extract().path("message");//извлекаем факт.сообщение об ошибке
        //сравниваем фактический и ожидаемый статус-код
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
        //сравниваем ожидаемое сообщение об ошибке и фактическое
        Assert.assertEquals("Incorrect error message", expectedMessage, actualMessage);

    }

    @Test
    @DisplayName("если обновлять емейл без авторизации, вернется ошибка 401")
    public void editUserEmailWithoutTokenReturnsStatusCode401() {
        int expectedStatusCode = 401; //ожидаемый статус-код
        ValidatableResponse editResponse = userClient
                .editWithoutAuthorization(userGenerator.getRandomEmail());//изменяем емейл пользователя

        int actualStatusCode = editResponse.extract().statusCode();//извлекаем фактический статус код
        String actualMessage = editResponse.extract().path("message");//извлекаем факт.сообщение об ошибке
        //сравниваем фактический и ожидаемый статус-код
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
        //сравниваем ожидаемое сообщение об ошибке и фактическое
        Assert.assertEquals("Incorrect error message", expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("если обновлять пароль без авторизации, вернется ошибка 401")
    public void editUserPasswordWithoutTokenReturnsStatusCode401() {
        int expectedStatusCode = 401; //ожидаемый статус-код
        ValidatableResponse editResponse = userClient
                .editWithoutAuthorization(userGenerator.getRandomPassword());//изменяем пароль пользователя

        int actualStatusCode = editResponse.extract().statusCode();//извлекаем фактический статус код
        String actualMessage = editResponse.extract().path("message");//извлекаем факт.сообщение об ошибке
        //сравниваем фактический и ожидаемый статус-код
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
        //сравниваем ожидаемое сообщение об ошибке и фактическое
        Assert.assertEquals("Incorrect error message", expectedMessage, actualMessage);

    }

    @Test
    @DisplayName("если обновлять имя без авторизации, вернется ошибка 401")
    public void editUserNameWithoutTokenReturnsStatusCode401() {
        int expectedStatusCode = 401; //ожидаемый статус-код
        ValidatableResponse editResponse = userClient
                .editWithoutAuthorization(userGenerator.getRandomName());//изменяем имя пользователя

        int actualStatusCode = editResponse.extract().statusCode();//извлекаем фактический статус код
        String actualMessage = editResponse.extract().path("message");//извлекаем факт.сообщение об ошибке
        //сравниваем фактический и ожидаемый статус-код
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
        //сравниваем ожидаемое сообщение об ошибке и фактическое
        Assert.assertEquals("Incorrect error message", expectedMessage, actualMessage);
    }
    }
