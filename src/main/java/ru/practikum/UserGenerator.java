package ru.practikum;
//класс-генератор тестовых данных для создания пользователя
public class UserGenerator {

    private final String EMAIL = "harrypotter1@mail.ru";
    private final String PASSWORD = "expeliarmus1";
    private final String NAME = "Harry";

//метод для получения данных пользователя с корректными данными
    public User getCorrectUserData() {
        return new User(this.EMAIL, this.PASSWORD, this.NAME);
    }
//метод для получения данных пользователя без емейла
    public User getUserWithoutEmail() {
        return new User(null, this.PASSWORD, this.NAME);
    }
//метод для получения данных пользователя без пароля
    public User getUserWithoutPassword() {
        return new User(this.EMAIL, null, this.NAME);
    }
//метод для получения данных пользователя без имени
    public User getUserWithoutName() {
        return new User(this.EMAIL, this.PASSWORD, null);
    }
}
