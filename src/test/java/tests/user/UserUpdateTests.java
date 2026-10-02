package tests.user;

import baseTests.BaseTests;
import io.restassured.response.Response;
import models.user.UserRequest;
import models.user.UserResponse;
import org.testng.annotations.Test;
import utils.TestData;
import utils.TestDataGenerator;

public class UserUpdateTests extends BaseTests {

    @Test
    public void updateUserByIdTest(){

        UserRequest user = TestDataGenerator.createUser();

        Response createdUserResponse = userService.createUser(user);

        softAssert.assertEquals(createdUserResponse.statusCode(), 200);

        UserResponse createdUserBody = createdUserResponse.as(UserResponse.class);

        String userId = createdUserBody.getId();

        String updatedFirstName = "Updated First Name";
        String updatedLastName = "Updated Last Name";

        UserRequest updatedUser = user
                .withFirstName(updatedFirstName)
                .withLastName(updatedLastName);

        Response updatedUserResponse = userService.updateUserById(updatedUser, userId);

        softAssert.assertEquals(updatedUserResponse.statusCode(), 200);

        UserResponse updatedUserBody = updatedUserResponse.as(UserResponse.class);

        softAssert.assertEquals(updatedUserBody.getId(), userId);
        softAssert.assertEquals(updatedUserBody.getFirstName(), updatedUser.getFirstName());
        softAssert.assertEquals(updatedUserBody.getLastName(), updatedUser.getLastName());
        softAssert.assertEquals(updatedUserBody.getEmail(), createdUserBody.getEmail());
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

        UserRequest user = TestDataGenerator.createUser();

        String nonExistingUserId = "aaaaaaaaaaaaaaaaaaaaaaaa";

        Response nonExistingUserIdResponse = userService.updateUserById(user, nonExistingUserId);

        softAssert.assertEquals(nonExistingUserIdResponse.statusCode(), 400);
        softAssert.assertEquals(nonExistingUserIdResponse.jsonPath().getString("error"), TestData.BODY_NOT_VALID);
        softAssert.assertAll();

    }
}
