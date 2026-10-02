package utils;

import com.github.javafaker.Faker;
import models.user.UserLocation;
import models.user.UserRequest;

import java.util.Locale;

public class TestDataGenerator {

    private static final Faker faker = new Faker(new Locale("en-US"));

    public static UserRequest createUser() {

        UserLocation userLocation = UserLocation.builder()
                .street(faker.address().streetAddress())
                .city(faker.address().city())
                .state(faker.address().state())
                .country(faker.address().country())
                .build();

        return UserRequest.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .phone(faker.phoneNumber().phoneNumber())
                .location(userLocation)
                .build();
    }
}