package tests.user;

import baseTests.BaseTests;
import io.restassured.response.Response;
import models.user.UserRequest;
import models.user.UserResponse;
import org.testng.annotations.Test;
import utils.TestData;
import utils.TestDataGenerator;

public class UserCreateTests extends BaseTests {

    @Test
    public void createUserTest(){

        UserRequest user = TestDataGenerator.createUser();

        Response createdUserResponse = userService.createUser(user);

        softAssert.assertEquals(createdUserResponse.statusCode(), 200);

        UserResponse createdUserBody = createdUserResponse.as(UserResponse.class);

        softAssert.assertEquals(createdUserBody.getFirstName(), user.getFirstName());
        softAssert.assertEquals(createdUserBody.getLastName(), user.getLastName());
        softAssert.assertEquals(createdUserBody.getEmail(), user.getEmail());
        softAssert.assertEquals(createdUserBody.getRegisterDate(), createdUserBody.getUpdatedDate());
        softAssert.assertNotNull(createdUserBody.getId(), "User ID should not be null");
        softAssert.assertFalse(createdUserBody.getId().isEmpty(), "User ID should not be empty");
        softAssert.assertAll();

    }

    @Test
    public void createUserWithInvalidEmailTest(){

        UserRequest user = TestDataGenerator.createUser()
                .withEmail("invalid-email");

        Response invalidEmailResponse = userService.createUser(user);

        softAssert.assertEquals(invalidEmailResponse.statusCode(), 400);
        softAssert.assertEquals(invalidEmailResponse.jsonPath().getString("error"), TestData.BODY_NOT_VALID);
        softAssert.assertEquals(invalidEmailResponse.jsonPath().getString("data.email"), "Path `email` is invalid (invalid-email).");
        softAssert.assertAll();

    }

    @Test
    public void createUserWithEmptyRequiredFieldsTest(){

        UserRequest user = TestDataGenerator.createUser()
                .withFirstName("")
                .withLastName("")
                .withEmail("");

        Response emptyRequiredFieldsResponse = userService.createUser(user);

        softAssert.assertEquals(emptyRequiredFieldsResponse.statusCode(), 400);
        softAssert.assertEquals(emptyRequiredFieldsResponse.jsonPath().getString("data.firstName"), "Path `firstName` is required.");
        softAssert.assertEquals(emptyRequiredFieldsResponse.jsonPath().getString("data.lastName"), "Path `lastName` is required.");
        softAssert.assertEquals(emptyRequiredFieldsResponse.jsonPath().getString("data.email"), "Path `email` is required.");
        softAssert.assertAll();

    }

}
