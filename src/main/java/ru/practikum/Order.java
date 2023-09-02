package ru.practikum;

import java.util.List;

//класс для создания заказа
public class Order {
    private List<String > ingredients;
//конструктор с параметрами и без для сериализации тестовых данных
    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public Order() {

    }

    public List<String> getIngredients() {

        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {

        this.ingredients = ingredients;
    }
}
