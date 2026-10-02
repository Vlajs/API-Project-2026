package baseTests;

import config.Config;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import services.UserService;

public class BaseTests {

    protected UserService userService;
    protected SoftAssert softAssert;

    @BeforeMethod
    public void setUpTests(){
        new Config().setUp();
        userService = new UserService();
        softAssert = new SoftAssert();
    }
}
