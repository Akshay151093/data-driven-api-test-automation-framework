package assertions;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;

public class UserAssertions {

    @Step("Verify response status code is {expectedStatusCode}")
    public void verifyStatusCode(Response response, int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode,
                "Status code verification failed");
    }

    @Step("Verify username is '{expectedUsername}'")
    public void verifyUsername(Response response, String expectedUsername) {
        Assert.assertEquals(
                response.jsonPath().getString("username"),
                expectedUsername,
                "Username verification failed"
        );
    }

    @Step("Verify first name is '{expectedFirstName}'")
    public void verifyFirstName(Response response, String expectedFirstName) {
        Assert.assertEquals(
                response.jsonPath().getString("firstName"),
                expectedFirstName,
                "First name verification failed"
        );
    }

    @Step("Verify last name is '{expectedLastName}'")
    public void verifyLastName(Response response, String expectedLastName) {
        Assert.assertEquals(
                response.jsonPath().getString("lastName"),
                expectedLastName,
                "Last name verification failed"
        );
    }

    @Step("Verify email is '{expectedEmail}'")
    public void verifyEmail(Response response, String expectedEmail) {
        Assert.assertEquals(
                response.jsonPath().getString("email"),
                expectedEmail,
                "Email verification failed"
        );
    }

    @Step("Verify phone number is '{expectedPhone}'")
    public void verifyPhone(Response response, String expectedPhone) {
        Assert.assertEquals(
                response.jsonPath().getString("phone"),
                expectedPhone,
                "Phone verification failed"
        );
    }

    @Step("Verify user status is '{expectedStatus}'")
    public void verifyUserStatus(Response response, int expectedStatus) {
        Assert.assertEquals(
                response.jsonPath().getInt("userStatus"),
                expectedStatus,
                "User status verification failed"
        );
    }

    @Step("Verify user ID is '{expectedId}'")
    public void verifyUserId(Response response, int expectedId) {
        Assert.assertEquals(
                response.jsonPath().getInt("id"),
                expectedId,
                "User ID verification failed"
        );
    }
}