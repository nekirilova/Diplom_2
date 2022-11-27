package ru.practikum;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private UserClient userClient;
    private UserGenerator userGenerator;
    private User user;
    private String token;
    ValidatableResponse response;

    @Before
    public void setUp() {
        userGenerator = new UserGenerator();
        user = userGenerator.getCorrectUserData();
        userClient = new UserClient();
    }

    @After
    public void cleanUp() {
       token = userClient.getToken(response);
     //  System.out.println(token);
       userClient.delete(token);
    }


    @Test
    public void userCanBeCreated() {
        int expectedStatusCode = 200;
        response = userClient.create(user);
        int actualStatusCode = response.extract().statusCode();
       // System.out.println(actualStatusCode);
        Assert.assertEquals("Incorrect status code",expectedStatusCode, actualStatusCode);
    }

    @Test
    public void createSameUserReturnsStatusCode403() {
        int expectedStatusCode = 403;
        response = userClient.create(user);
        ValidatableResponse badResponse = userClient.create(user);
        int actualStatusCode = badResponse.extract().statusCode();
       // System.out.println(actualStatusCode);
        Assert.assertEquals("Incorrect status code", expectedStatusCode, actualStatusCode);
    }

}
