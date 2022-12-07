package ru.practikum;
//класс для авторизации пользователя
public class LoginUser {
    private String email;
    private String password;
   //конструкторы для сериализации данных при авторизации пользователя
    public LoginUser(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public LoginUser() {

    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
