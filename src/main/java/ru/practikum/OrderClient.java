package ru.practikum;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

//класс методов работы с заказом
public class OrderClient extends Client{
    private final String GET_INGREDIENTS = "api/ingredients";

     public ValidatableResponse getIngredients() {
         return given()
                 .spec(getSpec())
                 .when()
                 .get(GET_INGREDIENTS)
                 .then();
     }

     public List<String> getRandomIngredients() {
        String[] ingredients = getIngredients().extract().path("_id");

     }
}
