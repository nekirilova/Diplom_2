package ru.practikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.*;

import static io.restassured.RestAssured.given;

//класс методов работы с заказом
public class OrderClient extends Client{
    private final String GET_INGREDIENTS = "api/ingredients";
    private final String CREATE_ORDER = "api/orders";
    private List<String> ingredients;
    @Step("Метод для получения списка доступных ингридентов")
     public List<String> getAllIngredients() {
         ingredients = given()
                 .spec(getSpec())
                 .when()
                 .get(GET_INGREDIENTS)
                 .then()
                 .extract()
                 .body()
                 .jsonPath()
                 .getList("data._id"); //получаем список хешей ингридентов
         return ingredients;
     }

     @Step("метод, который составляет список из трех рандомных ингредиентов")
     public List<String> getRandomIngredients() {
         Random random = new Random();
         List<String> randomIngredients = new ArrayList<>();
         for(int i = 0; i < 3; i++) {
             int index = random.nextInt(ingredients.size() - 1);
             randomIngredients.add(ingredients.get(index));}
            return randomIngredients;
         }
    @Step("метод, который генерирует неправильный хэш ингредиентов")
    public List<String> generateWrongIngredients() {
         Random random = new Random();
         List<String> randomWrongIngredients = new ArrayList<>();
         randomWrongIngredients.add("ingredient" + random.nextInt(1000000));
         return randomWrongIngredients;
    }

    @Step("метод для создания заказа с авторизацией")
         public ValidatableResponse create(Order order, String token) {
         return given()
                 .spec(getAuthSpec(token))
                 .body(order)
                 .when()
                 .post(CREATE_ORDER)
                 .then();
         }

    @Step("метод для создания заказа без авторизации")
    public ValidatableResponse createWithoutToken(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(CREATE_ORDER)
                .then();
    }

    @Step("метод для получения списка заказов авторизованного пользователя")
    public ValidatableResponse getOrdersListWithAuth(String token) {
         return given()
                 .spec(getAuthSpec(token))
                 .when()
                 .get(CREATE_ORDER).then();
    }

    @Step("метод для получения списка заказов неавторизованного пользователя")
    public ValidatableResponse getOrdersListWithoutAuth() {
        return given()
                .spec(getSpec())
                .when()
                .get(CREATE_ORDER).then();
    }

     }

