package steps;

import endpoints.UserEndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import payload.User;

public class UserSteps {

    @Step("Create user with username: {user.username}")
    public Response createUser(User user){
        return UserEndPoints.createUser(user);
    }

    @Step("Get user with username: {user.username}")
    public Response getUser(String userName){
        return UserEndPoints.readUser(userName);
    }

    @Step("Update user '{username}'")
    public Response updateUser(String username, User user) {
        return UserEndPoints.updateUser(username, user);
    }

    @Step("Update user '{username}'")
    public Response deleteUser(String username) {
        return UserEndPoints.deleteUser(username);
    }
}
