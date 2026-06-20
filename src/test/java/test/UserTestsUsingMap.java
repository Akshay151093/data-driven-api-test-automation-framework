package test;

import assertions.UserAssertions;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import payload.User;
import steps.UserSteps;
import utilities.UserDataBuilder;

@Epic("PET-STORE API")
@Feature("USER API : MAP")
public class UserTestsUsingMap {

    UserAssertions userAssertions;
    UserSteps userSteps;

    public UserTestsUsingMap(){
        userAssertions = new UserAssertions();
        userSteps = new UserSteps();
    }

    @Test(priority = 1)
    @Story("Create user")
    @Description("Verify that a new user can be created successfully")
    void testCreateUser1() {
        User payload = UserDataBuilder.getTestDataUserPayload("User_1");
        Response createResponse = userSteps.createUser(payload);
        userAssertions.verifyStatusCode(createResponse, 200);
    }

    @Test (priority = 2, dependsOnMethods = "testCreateUser1")
    @Story("Get user by username")
    @Description("Verify user can be fetch successfully")
    void testGetUser1(){
        User payload = UserDataBuilder.getTestDataUserPayload("User_1");
        Response getResponse = userSteps.getUser(payload.getUsername());
        userAssertions.verifyStatusCode(getResponse,200);
        userAssertions.verifyUsername(getResponse, payload.getUsername());
        userAssertions.verifyFirstName(getResponse, payload.getEmail());
        userAssertions.verifyLastName(getResponse, payload.getPhone());
    }

    @Test(priority = 3)
    @Story("Create user")
    @Description("Verify that a new user can be created successfully")
    void testCreateUser2() {
        User payload = UserDataBuilder.getTestDataUserPayload("User_2");
        Response createResponse = userSteps.createUser(payload);
        userAssertions.verifyStatusCode(createResponse, 200);
    }

    @Test (priority = 4, dependsOnMethods = "testCreateUser2")
    @Story("Get user by username")
    @Description("Verify user can be fetch successfully")
    void testGetUser2(){
        User payload = UserDataBuilder.getTestDataUserPayload("User_2");
        Response getResponse = userSteps.getUser(payload.getUsername());
        userAssertions.verifyStatusCode(getResponse,200);
        userAssertions.verifyUsername(getResponse, payload.getUsername());
        userAssertions.verifyFirstName(getResponse, payload.getEmail());
        userAssertions.verifyLastName(getResponse, payload.getPhone());
    }
}
