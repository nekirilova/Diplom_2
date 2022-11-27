package ru.practikum;

public class UserGenerator {

    private final String EMAIL = "harrypotter@mail.ru";
    private final String PASSWORD = "expeliarmus";
    private final String NAME = "Harry";


    public User getCorrectUserData() {
        return new User(this.EMAIL, this.PASSWORD, this.NAME);
    }
}
