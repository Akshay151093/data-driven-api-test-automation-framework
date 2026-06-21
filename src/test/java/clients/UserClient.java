package clients;

import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;
import models.User;
import utils.LogManagerUtil;

import static io.restassured.RestAssured.*;

public class UserClient {

    private static final Logger logger = LogManagerUtil.getLogger(UserClient.class);

    public static Response createUser(User payload){
        logger.info("Calling Create User API");
        logger.debug("Endpoint : {}", Routes.USER);
        logger.debug("Request Payload : {}", payload.toString());

        Response createResponse = given()
                .spec(RequestSpecFactory.defaultRequestSpec())
                .body(payload)
        .when()
                .post(Routes.USER);

        logger.info("Create User completed");
        logger.debug("Response Body : {}", createResponse.asPrettyString());
        return createResponse;
    }

    public static Response getUser (String name){
        logger.info("Calling Get User API");
        logger.debug("Endpoint : {}", Routes.USER_BY_USERNAME);
        logger.debug("Request Name : {}", name);

        Response getResponse = given()
                .spec(RequestSpecFactory.defaultRequestSpec())
                .pathParam("username",name)
        .when()
                .get(Routes.USER_BY_USERNAME);

        logger.info("Get User completed");
        logger.debug("Response Body : {}", getResponse.asPrettyString());
        return getResponse;
    }

    public static Response updateUser (String name, User payload){
        logger.info("Calling Update User API");
        logger.debug("Endpoint : {}", Routes.USER_BY_USERNAME);
        logger.debug("Request Payload : {}", payload);

        Response putResponse = given()
                .spec(RequestSpecFactory.defaultRequestSpec())
                .pathParam("username", name)
                .body(payload)
        .when()
                .put(Routes.USER_BY_USERNAME);

        logger.info("Update User completed");
        logger.debug("Response Body : {}", putResponse.asPrettyString());
        return putResponse;
    }

    public static Response deleteUser (String name){
        logger.info("Calling Delete User API");
        logger.debug("Endpoint : {}", Routes.USER_BY_USERNAME);
        logger.debug("Request Name : {}", name);
        Response deleteResponse = given()
                .spec(RequestSpecFactory.defaultRequestSpec())
                .pathParam("username",name)
        .when()
                .delete(Routes.USER_BY_USERNAME);
        logger.info("Delete User completed");
        logger.debug("Response Body : {}", deleteResponse.asPrettyString());
        return deleteResponse;
    }
}
