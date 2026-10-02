package tests.user;

import baseTests.BaseTests;
import io.restassured.response.Response;
import models.user.GetAllUsersResponse;
import models.user.UserRequest;
import models.user.UserResponse;
import org.testng.annotations.Test;
import utils.TestData;
import utils.TestDataGenerator;

public class UserGetTests extends BaseTests {

    @Test
    public void getAllUsersTest(){

        Response allUsersResponse = userService.getAllUsers();

        softAssert.assertEquals(allUsersResponse.statusCode(), 200);

        GetAllUsersResponse allUsersBody = allUsersResponse.as(GetAllUsersResponse.class);

        softAssert.assertEquals(allUsersBody.getData().get(0).getId(), "60d0fe4f5311236168a109cb");
        softAssert.assertEquals(allUsersBody.getPage(), 0);
        softAssert.assertEquals(allUsersBody.getLimit(), 20);
        softAssert.assertTrue(allUsersBody.getTotal() > 0);
        softAssert.assertAll();

    }

    @Test
    public void getUserByIdTest(){

        UserRequest user = TestDataGenerator.createUser();

        Response createdUserResponse = userService.createUser(user);

        softAssert.assertEquals(createdUserResponse.statusCode(), 200);

        UserResponse createdUserBody = createdUserResponse.as(UserResponse.class);

        String userId = createdUserBody.getId();

        Response userByIdResponse = userService.getUserById(userId);

        softAssert.assertEquals(userByIdResponse.statusCode(), 200);

        UserResponse userByIdBody = userByIdResponse.as(UserResponse.class);

        softAssert.assertEquals(userByIdBody.getId(), userId);
        softAssert.assertEquals(userByIdBody.getFirstName(), user.getFirstName());
        softAssert.assertEquals(userByIdBody.getLastName(), user.getLastName());
        softAssert.assertEquals(userByIdBody.getEmail(), user.getEmail());
        softAssert.assertAll();

    }

    @Test
    public void getUserByInvalidIdFormatTest(){

        String invalidUserId = "123abc";

        Response invalidUserIdResponse = userService.getUserById(invalidUserId);

        softAssert.assertEquals(invalidUserIdResponse.statusCode(), 400);
        softAssert.assertEquals(invalidUserIdResponse.jsonPath().getString("error"), TestData.PARAMS_NOT_VALID);
        softAssert.assertAll();

    }

    @Test
    public void getUserByNonExistingIdTest(){

        String nonExistingUserId = "aaaaaaaaaaaaaaaaaaaaaaaa";

        Response nonExistingUserIdResponse = userService.getUserById(nonExistingUserId);

        softAssert.assertEquals(nonExistingUserIdResponse.statusCode(), 404);
        softAssert.assertEquals(nonExistingUserIdResponse.jsonPath().getString("error"), TestData.RESOURCE_NOT_FOUND);
        softAssert.assertAll();

    }
}
