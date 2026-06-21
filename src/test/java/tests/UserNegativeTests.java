package tests;

import clients.UserClient;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

@Epic("PET-STORE API")
@Feature("USER API : NEGATIVE TESTS")
public class UserNegativeTests extends BaseTest {

    @Test(groups = {"negative", "user"})
    @Story("Get non-existing user")
    @Description("Verify API returns 404 for non-existing username")
    void testGetNonExistingUser() {
        Response response = UserClient.getUser("non_existing_user_123456789");
        userAssertions.verifyStatusCode(response, 404);
    }
}
