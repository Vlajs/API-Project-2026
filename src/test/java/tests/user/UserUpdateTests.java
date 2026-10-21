package tests.user;

import baseTests.BaseTests;
import io.restassured.response.Response;
import models.user.UserRequest;
import models.user.UserResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestData;
import utils.TestDataGenerator;

public class UserUpdateTests extends BaseTests {

    // Observed behavior (not documented): a changed email in PUT is ignored, the response keeps the original email.
    @Test
    public void updateUserByIdTest(){

        UserRequest user = TestDataGenerator.createUser();

        Response createdUserResponse = userService.createUser(user);

        Assert.assertEquals(createdUserResponse.statusCode(), 200);

        UserResponse createdUserBody = createdUserResponse.as(UserResponse.class);

        String userId = createdUserBody.getId();

        String updatedFirstName = "Updated First Name";
        String updatedLastName = "Updated Last Name";

        UserRequest updatedUser = user
                .withFirstName(updatedFirstName)
                .withLastName(updatedLastName);

        Response updatedUserResponse = userService.updateUserById(updatedUser, userId);

        Assert.assertEquals(updatedUserResponse.statusCode(), 200);

        UserResponse updatedUserBody = updatedUserResponse.as(UserResponse.class);

        softAssert.assertEquals(updatedUserBody.getId(), userId);
        softAssert.assertEquals(updatedUserBody.getFirstName(), updatedUser.getFirstName());
        softAssert.assertEquals(updatedUserBody.getLastName(), updatedUser.getLastName());
        softAssert.assertEquals(updatedUserBody.getEmail(), createdUserBody.getEmail());
        softAssert.assertEquals(updatedUserBody.getRegisterDate(), createdUserBody.getRegisterDate());
        softAssert.assertNotEquals(updatedUserBody.getUpdatedDate(), createdUserBody.getUpdatedDate());
        softAssert.assertAll();

    }

    @Test
    public void updateUserByInvalidIdFormatTest(){

        UserRequest user = TestDataGenerator.createUser();

        String invalidUserId = "123abc";

        Response invalidUserIdResponse = userService.updateUserById(user, invalidUserId);

        softAssert.assertEquals(invalidUserIdResponse.statusCode(), 400);
        softAssert.assertEquals(invalidUserIdResponse.jsonPath().getString("error"), TestData.PARAMS_NOT_VALID);
        softAssert.assertAll();

    }

    @Test
    public void updateUserByNonExistingIdTest(){

        UserRequest user = UserRequest.builder()
                .firstName("Updated First Name")
                .build();

        String nonExistingUserId = "aaaaaaaaaaaaaaaaaaaaaaaa";

        Response nonExistingUserIdResponse = userService.updateUserById(user, nonExistingUserId);

        softAssert.assertEquals(nonExistingUserIdResponse.statusCode(), 400);
        softAssert.assertEquals(nonExistingUserIdResponse.jsonPath().getString("error"), TestData.BODY_NOT_VALID);
        softAssert.assertAll();

    }

    @Test
    public void updateUserPartiallyTest(){

        UserRequest user = TestDataGenerator.createUser();

        Response createdUserResponse = userService.createUser(user);

        Assert.assertEquals(createdUserResponse.statusCode(), 200);

        UserResponse createdUserBody = createdUserResponse.as(UserResponse.class);

        String userId = createdUserBody.getId();

        UserRequest partialUpdate = UserRequest.builder()
                .firstName("Partially Updated")
                .build();

        Response updatedUserResponse = userService.updateUserById(partialUpdate, userId);

        Assert.assertEquals(updatedUserResponse.statusCode(), 200);

        UserResponse updatedUserBody = updatedUserResponse.as(UserResponse.class);

        softAssert.assertEquals(updatedUserBody.getId(), userId);
        softAssert.assertEquals(updatedUserBody.getFirstName(), partialUpdate.getFirstName());
        softAssert.assertEquals(updatedUserBody.getLastName(), createdUserBody.getLastName());
        softAssert.assertEquals(updatedUserBody.getEmail(), createdUserBody.getEmail());
        softAssert.assertEquals(updatedUserBody.getPhone(), createdUserBody.getPhone());
        softAssert.assertEquals(updatedUserBody.getLocation(), createdUserBody.getLocation());
        softAssert.assertAll();

    }
}
