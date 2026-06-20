package utilities;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import payload.User;

import java.util.LinkedHashMap;

public class UserDataBuilder {

    static User user;
    static Faker faker;
    private static TestDataManager testDataManager;

     UserDataBuilder(){
         faker = new Faker();
         user = new User();
         testDataManager = new TestDataManager();
     }

    @Step("Create user payload all data")
    public static User getRandomUserPayload() {
        user.setId(faker.idNumber().hashCode());
        user.setUsername(faker.name().username());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().safeEmailAddress());
        user.setPassword(faker.internet().password());
        user.setPhone(faker.phoneNumber().cellPhone());
        user.setUserStatus(0);
        return user;
    }

    @Step("Update user payload with new email")
    public static void updateUserEmail(User user) {
        user.setEmail(faker.internet().safeEmailAddress());
    }

    @Step("Update user payload with new phone")
    public static void updateUserPhone(User user) {
        user.setPhone(faker.phoneNumber().cellPhone());
    }

    public static User getTestDataUserPayload(String testDataLabel){
        LinkedHashMap<String, String> testData = testDataManager.
                getTestData(testDataLabel, "TestData_Users", "Input_1");
        user.setId(Integer.parseInt(testData.get("ID")));
        user.setUsername(testData.get("UserName"));
        user.setFirstName(testData.get("FirstName"));
        user.setLastName(testData.get("LastName"));
        user.setEmail(testData.get("Email"));
        user.setPassword(testData.get("Password"));
        user.setPhone(testData.get("Phone"));
        user.setUserStatus(Integer.parseInt(testData.get("Status")));
        return user;
    }
}
