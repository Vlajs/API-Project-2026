package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String GET_ALL_USERS = "/user";
    public static final String GET_USER_BY_ID = "/user/{id}";
    public static final String DELETE_USER_BY_ID = "/user/{id}";
    public static final String CREATE_USER = "/user/create";
    public static final String UPDATE_USER = "/user/{id}";

    public static final String PROJECT_ROOT = System.getProperty("user.dir");
}
