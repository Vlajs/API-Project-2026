package tests.user;

import baseTests.BaseTests;
import io.restassured.response.Response;
import models.user.UserRequest;
import models.user.UserResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestData;
import utils.TestDataGenerator;

public class UserDeleteTests extends BaseTests {

    @Test
    public void deleteUserByIdTest(){

        UserRequest user = TestDataGenerator.createUser();

        Response createdUserResponse = userService.createUser(user);

        Assert.assertEquals(createdUserResponse.statusCode(), 200);

        UserResponse createdUserBody = createdUserResponse.as(UserResponse.class);

        String userId = createdUserBody.getId();

        Response deletedUserResponse = userService.deleteUserById(userId);

        softAssert.assertEquals(deletedUserResponse.statusCode(), 200);
        softAssert.assertEquals(deletedUserResponse.jsonPath().getString("id"), userId);

        Response getDeletedUserResponse = userService.getUserById(userId);

        softAssert.assertEquals(getDeletedUserResponse.statusCode(), 404);
        softAssert.assertEquals(getDeletedUserResponse.jsonPath().getString("error"), TestData.RESOURCE_NOT_FOUND);
        softAssert.assertAll();

    }

    @Test
    public void confirmDeletedUserByIdTest(){

        UserRequest user = TestDataGenerator.createUser();

        Response createdUserResponse = userService.createUser(user);

        Assert.assertEquals(createdUserResponse.statusCode(), 200);

        UserResponse createdUserBody = createdUserResponse.as(UserResponse.class);

        String userId = createdUserBody.getId();

        Response deletedUserResponse = userService.deleteUserById(userId);

        Assert.assertEquals(deletedUserResponse.statusCode(), 200);

        Response confirmDeletedUserResponse = userService.deleteUserById(userId);

        softAssert.assertEquals(confirmDeletedUserResponse.statusCode(), 404);
        softAssert.assertEquals(confirmDeletedUserResponse.jsonPath().getString("error"), TestData.RESOURCE_NOT_FOUND);
        softAssert.assertAll();

    }

    @Test
    public void deleteUserByInvalidIdFormatTest(){

        String invalidUserId = "123abc";

        Response invalidUserIdResponse = userService.deleteUserById(invalidUserId);

        softAssert.assertEquals(invalidUserIdResponse.statusCode(), 400);
        softAssert.assertEquals(invalidUserIdResponse.jsonPath().getString("error"), TestData.PARAMS_NOT_VALID);
        softAssert.assertAll();

    }

}
