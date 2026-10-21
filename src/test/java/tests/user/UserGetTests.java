package tests.user;

import baseTests.BaseTests;
import io.restassured.response.Response;
import models.user.GetAllUsersResponse;
import models.user.UserRequest;
import models.user.UserResponse;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.TestData;
import utils.TestDataGenerator;

import java.util.Collections;
import java.util.List;

public class UserGetTests extends BaseTests {

    @Test
    public void getAllUsersTest(){

        Response allUsersResponse = userService.getAllUsers();

        Assert.assertEquals(allUsersResponse.statusCode(), 200);

        GetAllUsersResponse allUsersBody = allUsersResponse.as(GetAllUsersResponse.class);

        softAssert.assertEquals(allUsersBody.getData().get(0).getId(), "60d0fe4f5311236168a109cb");
        softAssert.assertEquals(allUsersBody.getPage(), 0);
        softAssert.assertEquals(allUsersBody.getLimit(), 20);
        softAssert.assertTrue(allUsersBody.getTotal() > 0);
        softAssert.assertAll();

    }

    @Test
    public void getAllUsersWithPaginationTest(){

        int page = 1;
        int limit = 10;

        Response paginatedUsersResponse = userService.getAllUsers(page, limit);

        Assert.assertEquals(paginatedUsersResponse.statusCode(), 200);

        GetAllUsersResponse paginatedUsersBody = paginatedUsersResponse.as(GetAllUsersResponse.class);

        softAssert.assertEquals(paginatedUsersBody.getPage(), page);
        softAssert.assertEquals(paginatedUsersBody.getLimit(), limit);
        softAssert.assertEquals(paginatedUsersBody.getData().size(), limit);
        softAssert.assertAll();

    }

    @Test
    public void getAllUsersDifferentPagesTest(){

        int limit = 10;

        Response firstPageResponse = userService.getAllUsers(0, limit);

        Assert.assertEquals(firstPageResponse.statusCode(), 200);

        Response secondPageResponse = userService.getAllUsers(1, limit);

        Assert.assertEquals(secondPageResponse.statusCode(), 200);

        List<String> firstPageIds = firstPageResponse.as(GetAllUsersResponse.class)
                .getData().stream().map(UserResponse::getId).toList();

        List<String> secondPageIds = secondPageResponse.as(GetAllUsersResponse.class)
                .getData().stream().map(UserResponse::getId).toList();

        softAssert.assertTrue(Collections.disjoint(firstPageIds, secondPageIds), "Pages should not share users");
        softAssert.assertAll();

    }

    @DataProvider(name = "validLimits")
    public Object[][] validLimits(){

        return new Object[][]{{5}, {50}, {25}};
    }

    @Test(dataProvider = "validLimits")
    public void getAllUsersLimitBoundariesTest(int limit){

        Response usersResponse = userService.getAllUsers(0, limit);

        Assert.assertEquals(usersResponse.statusCode(), 200);

        GetAllUsersResponse usersBody = usersResponse.as(GetAllUsersResponse.class);

        softAssert.assertEquals(usersBody.getLimit(), limit);
        softAssert.assertEquals(usersBody.getData().size(), limit);
        softAssert.assertAll();

    }

    @DataProvider(name = "outOfRangeLimits")
    public Object[][] outOfRangeLimits(){

        return new Object[][]{{4, 5}, {51, 50}};
    }

    @Test(dataProvider = "outOfRangeLimits")
    public void getAllUsersLimitOutOfRangeTest(int requestedLimit, int expectedLimit){

        // Observed behavior (not documented): an out-of-range limit is silently adjusted to the nearest allowed value.
        Response usersResponse = userService.getAllUsers(0, requestedLimit);

        Assert.assertEquals(usersResponse.statusCode(), 200);

        GetAllUsersResponse usersBody = usersResponse.as(GetAllUsersResponse.class);

        softAssert.assertEquals(usersBody.getLimit(), expectedLimit);
        softAssert.assertEquals(usersBody.getData().size(), expectedLimit);
        softAssert.assertAll();

    }

    @Test
    public void getUserByIdTest(){

        UserRequest user = TestDataGenerator.createUser();

        Response createdUserResponse = userService.createUser(user);

        Assert.assertEquals(createdUserResponse.statusCode(), 200);

        UserResponse createdUserBody = createdUserResponse.as(UserResponse.class);

        String userId = createdUserBody.getId();

        Response userByIdResponse = userService.getUserById(userId);

        Assert.assertEquals(userByIdResponse.statusCode(), 200);

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
