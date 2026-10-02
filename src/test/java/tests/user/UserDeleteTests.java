package tests.user;

import baseTests.BaseTests;
import io.restassured.response.Response;
import models.user.UserRequest;
import models.user.UserResponse;
import org.testng.annotations.Test;
import utils.TestData;
import utils.TestDataGenerator;

public class UserDeleteTests extends BaseTests {

    @Test
    public void deleteUserByIdTest(){

        UserRequest user = TestDataGenerator.createUser();

        Response createdUserResponse = userService.createUser(user);

        softAssert.assertEquals(createdUserResponse.statusCode(), 200);

        UserResponse createdUserBody = createdUserResponse.as(UserResponse.class);

        String userId = createdUserBody.getId();

        Response deletedUserResponse = userService.deleteUserById(userId);

        softAssert.assertEquals(deletedUserResponse.statusCode(), 200);
        softAssert.assertEquals(deletedUserResponse.jsonPath().getString("id"), userId);
        softAssert.assertAll();

    }

    @Test
    public void confirmDeletedUserByIdTest(){

        UserRequest user = TestDataGenerator.createUser();

        Response createdUserResponse = userService.createUser(user);

        softAssert.assertEquals(createdUserResponse.statusCode(), 200);

        UserResponse createdUserBody = createdUserResponse.as(UserResponse.class);

        String userId = createdUserBody.getId();

        Response deletedUserResponse = userService.deleteUserById(userId);

        softAssert.assertEquals(deletedUserResponse.statusCode(), 200);

        Response confirmDeletedUserResponse = userService.deleteUserById(userId);

        softAssert.assertEquals(confirmDeletedUserResponse.statusCode(), 404);
        softAssert.assertEquals(confirmDeletedUserResponse.jsonPath().getString("error"), TestData.RESOURCE_NOT_FOUND);
        softAssert.assertAll();

    }

}
