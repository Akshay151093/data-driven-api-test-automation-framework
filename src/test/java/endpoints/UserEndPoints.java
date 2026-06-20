package endpoints;

/* Created to perform CRUD operations for user module */

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payload.User;

import static io.restassured.RestAssured.*;

public class UserEndPoints {

    public static Response createUser(User payload){
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)

        .when()
                .post(Routes.post_url);
    }

    public static Response readUser (String name){
        return given()
                        .pathParam("username",name)
        .when()
                .get(Routes.get_url);
    }

    public static Response updateUser (String name, User payload){
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("username", name)
                .body(payload)

        .when()
                .put(Routes.put_url);
    }

    public static Response deleteUser (String name){
        return given()
                .pathParam("username",name)
        .when()
                .delete(Routes.delete_url);
    }
}
