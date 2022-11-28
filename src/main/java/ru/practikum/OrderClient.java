package ru.practikum;

import io.restassured.response.ValidatableResponse;

import java.util.*;

import static io.restassured.RestAssured.given;

//класс методов работы с заказом
public class OrderClient extends Client{
    private final String GET_INGREDIENTS = "api/ingredients";
    private final String CREATE_ORDER = "api/orders";
    private List<String> ingredients;
//Метод для получения списка доступных ингридентов
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

     //метод, который составляет список из трех рандомных ингредиентов
     public List<String> getRandomIngredients() {
         Random random = new Random();
         List<String> randomIngredients = new ArrayList<>();
         for(int i = 0; i < 3; i++) {
             int index = random.nextInt(ingredients.size() - 1);
             randomIngredients.add(ingredients.get(index));}
            return randomIngredients;
         }

         public ValidatableResponse create(Order order, String token) {
         return given()
                 .spec(getAuthSpec(token))
                 .body(order)
                 .when()
                 .post(CREATE_ORDER)
                 .then();
         }
     }

