

package data;

import com.github.javafaker.Faker;

public class CourierData {
    private static Faker user = new Faker();
    public static String getRandomLogin() {
        return user.name().lastName() + System.currentTimeMillis();
    }
    public static String getRandomPassword() {
        return user.regexify("[0-9]{4}");
    }
    public static final String FIRSTNAME = "vika";
    public static final String COURIER_CREATE_PATH = "/api/v1/courier";
    public static final String COURIER_AUTHORIZATION_PATH = "/api/v1/courier/login";
    public static final String COURIER_DELETE_PATH = "/api/v1/courier/";
}
