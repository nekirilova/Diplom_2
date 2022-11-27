package ru.practikum;

import java.util.Random;

//класс-генератор тестовых данных для создания пользователя
public class UserGenerator {
    Random random = new Random();
    private final String EMAIL = "harrypotter1@mail.ru";
    private final String PASSWORD = "expeliarmus!";
    private final String NAME = "Harry";
    private final String INCORRECT_EMAIL = "voldemort@mail.ru";
    private final String INCORRECT_PASSWORD = "avadakedavra";

    private final String NEW_EMAIL = "email" + random.nextInt(10000);
    private final String NEW_PASSWORD = "pass" + random.nextInt(10000);
    private final String NEW_NAME = "name" + random.nextInt(10000);

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
    //метод для получения корректных данных пользователя для логина
    public LoginUser getCorrectLoginData() {
        return new LoginUser(this.EMAIL, this.PASSWORD);
    }

    //методы для получения некорректных данных пользователя для логина
    public LoginUser getIncorrectEmailLoginData() {
        return new LoginUser(this.INCORRECT_EMAIL, this.PASSWORD);
    }

    public LoginUser getIncorrectPasswordLoginData() {
        return new LoginUser(this.EMAIL, this.INCORRECT_PASSWORD);
    }

    //методы для обновления данных пользователя
    public User getRandomUser() {
        return new User(this.NEW_EMAIL, this.NEW_PASSWORD, this.NEW_NAME);
    } //обновить все данные сразу

    public User getRandomEmail() {
        return new User(this.NEW_EMAIL, this.PASSWORD, this.NAME);
    } //обновить только емейл
    public User getRandomPassword() {
        return new User(this.EMAIL, this.NEW_PASSWORD, this.NAME);
    }//обновить только пароль
    public User getRandomName() {
        return new User(this.EMAIL, this.PASSWORD, this.NEW_NAME);
    } //обновить только имя
}
