package test;

import assertions.UserAssertions;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.*;
import payload.User;
import steps.UserSteps;
import utilities.DataProviders;

@Epic("PET-STORE API")
@Feature("USER API : DATA PROVIDER")
public class UserTestsUsingDataProvider {

    User payload;
    UserAssertions userAssertions;
    UserSteps userSteps;

    public UserTestsUsingDataProvider(){
        userAssertions = new UserAssertions();
        userSteps = new UserSteps();
    }

    @Test(priority = 1, dataProvider = "payload", dataProviderClass = DataProviders.class)
    @Story("Create users")
    @Description("Verify that a new user can be created successfully")
    void testCreateUsers(String id, String un, String fn,
                         String ln, String e, String pwd, String ph, String s) {
        payload = new User();
        payload.setId(Integer.parseInt(id));
        payload.setUsername(un);
        payload.setFirstName(fn);
        payload.setLastName(ln);
        payload.setEmail(e);
        payload.setPassword(pwd);
        payload.setPhone(ph);
        payload.setUserStatus(Integer.parseInt(s));
        Response createResponse = userSteps.createUser(payload);
        userAssertions.verifyStatusCode(createResponse, 200);
    }

    @Test (priority = 2, dependsOnMethods = "testCreateUsers", dataProvider = "usernames", dataProviderClass = DataProviders.class)
    @Story("Get users")
    @Description("Verify user can be fetch successfully")
    void testGetUsers(String un){
        Response getResponse = userSteps.getUser(un);
        userAssertions.verifyStatusCode(getResponse,200);
        userAssertions.verifyUsername(getResponse, payload.getUsername());
        userAssertions.verifyFirstName(getResponse, payload.getEmail());
        userAssertions.verifyLastName(getResponse, payload.getPhone());

    }

    @Test (priority = 3, dependsOnMethods = "testGetUsers", dataProvider = "usernames", dataProviderClass = DataProviders.class)
    @Story("Delete users")
    @Description("Verify that user can be deleted successfully")
    void testDeleteUsers(String un){
        Response deleteResponse =userSteps.deleteUser(un);
        userAssertions.verifyStatusCode(deleteResponse,200);
        Response getResponse = userSteps.getUser(un);
        userAssertions.verifyStatusCode(getResponse,404);
    }
}
