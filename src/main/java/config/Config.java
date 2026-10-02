package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.TestData;

import java.util.HashMap;
import java.util.Map;

public class Config {

    public void setUp(){
        Map<String, String> headers = new HashMap<>();

        headers.put("app-id", TestData.APP_ID);
        headers.put("Content-Type", TestData.JSON_CONTENT_TYPE);
        headers.put("Accept", TestData.JSON_CONTENT_TYPE);

        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setRelaxedHTTPSValidation()
                .setBaseUri(TestData.BASE_URL)
                .setBasePath(TestData.BASE_PATH)
                .addHeaders(headers)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectHeader("Content-Type", TestData.JSON_RESPONSE_CONTENT_TYPE)
                .build();

        RestAssured.requestSpecification = requestSpecification;
        RestAssured.responseSpecification = responseSpecification;

    }
}
