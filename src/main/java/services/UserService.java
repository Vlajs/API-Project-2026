package services;

import io.restassured.response.Response;
import models.user.GetAllUsersResponse;
import models.user.UserRequest;
import models.user.UserResponse;
import utils.Constants;

import static io.restassured.RestAssured.given;

public class UserService {

    public Response createUser(UserRequest userRequest) {

        return given()
                .body(userRequest)
                .when()
                .post(Constants.CREATE_USER)
                .then()
                .extract()
                .response();
    }

    public Response getAllUsers(){

        return given()
                .when()
                .get(Constants.GET_ALL_USERS)
                .then()
                .extract()
                .response();
    }
    public Response getUserById(String userId){

        return given()
                .pathParams("id", userId)
                .when()
                .get(Constants.GET_USER_BY_ID)
                .then()
                .extract()
                .response();
    }

    public Response updateUserById(UserRequest userRequest, String userId) {

        return given()
                .pathParams("id", userId)
                .body(userRequest)
                .when()
                .put(Constants.UPDATE_USER)
                .then()
                .extract()
                .response();
    }

    public Response deleteUserById(String userId) {

        return given()
                .pathParams("id", userId)
                .when()
                .delete(Constants.DELETE_USER_BY_ID)
                .then()
                .extract()
                .response();
    }
}