package ru.practikum;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
//класс с настройками для запросов к API
public class Client {
    //базовый URL сайта
    private static final String BASE_URI = "https://stellarburgers.nomoreparties.site/";

    //метод для получения стандартных настроек
    protected RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI)
                .build();
    }

    //метод для получения настроек с токеном для авторизации
    protected RequestSpecification getAuthSpec(String token) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI)
                .addHeader("Authorization", token)
                .build();
    }
}
