import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierLoginRequestModel;
import model.CourierModel;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static data.CourierData.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static steps.CourierSteps.*;

public class LoginCourierTest extends BaseApiTest {

    private CourierModel courier;
    private Integer courierId;
    @Before
    public void createCourierForTest() {
        // Создаём курьера
        courier = new CourierModel(getRandomLogin(), getRandomPassword(), FIRSTNAME);
        Response createResponse = createCourier(courier);
        createResponse
                .then()
                .log().all()
                .statusCode(201)
                .body("ok", equalTo(true));
        if (createResponse.body().jsonPath().get("id") != null) {
            courierId = createResponse.body().jsonPath().getInt("id");
        }

    }

    @Test
    @DisplayName("Login Courier Success")
    @Description("Проверяем, что курьер может авторизоваться и успешный запрос возвращает id")
    public void testLoginCourierSuccess() {

        // Логинимся со всеми обязательными полями для положительной проверки и получаем id
        CourierLoginRequestModel loginRequest = new CourierLoginRequestModel(courier.getLogin(), courier.getPassword());
        Response loginResponse = loginCourier(loginRequest);
        loginResponse
        .then()
                .log().all()
                .statusCode(200)
                .body("id", notNullValue())
                .body("id", greaterThan(0));

    }
    @Test
    @DisplayName("Login Courier Fails Bad Password")
    @Description("Проверяем, что система вернёт ошибку, если неправильно указать пароль")
    public void testLoginCourierFailsBadPassword() {

        // Логинимся с несуществующем паролем
        CourierLoginRequestModel loginRequestBad = new CourierLoginRequestModel(courier.getLogin(), "1");
        Response loginResponseBad = loginCourier(loginRequestBad);
        loginResponseBad
                .then()
                .log().all()
                .statusCode(404)
                .body("message", Matchers.containsString("Учетная запись не найдена"));

    }

    @Test
    @DisplayName("Login Courier Fails Bad Login")
    @Description("Проверяем, что система вернёт ошибку, если неправильно указать логин")
    public void testLoginCourierFailsBadLogin() {
        // Логинимся с несуществующем логином
        CourierLoginRequestModel loginRequestBad = new CourierLoginRequestModel("n", courier.getPassword());
        Response loginResponseBad = loginCourier(loginRequestBad);
        loginResponseBad
                .then()
                .log().all()
                .statusCode(404)
                .body("message", Matchers.containsString("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login Courier Fails Without Password Field")
    @Description("Проверяем, что система вернёт ошибку, если если поля пароль нет, запрос возвращает ошибку")
    public void testLoginCourierFailsWithoutPasswordField() {
        // Логинимся не заполняя поле пароль, но тест падает с ошибкой 504
        CourierLoginRequestModel loginRequestWithoutPassword =
                new CourierLoginRequestModel(courier.getLogin(), null);

        Response response = loginCourier(loginRequestWithoutPassword);
        response
                .then()
                .log().all()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login Courier Fails Without Login Field")
    @Description("Проверяем, что система вернёт ошибку, если если поля логин нет, запрос возвращает ошибку")
    public void testLoginCourierFailsWithoutLoginField() {
        // Логинимся не заполняя поле логин
        CourierLoginRequestModel loginRequestWithoutLogin =
                new CourierLoginRequestModel(null, courier.getPassword());

        Response response = loginCourier(loginRequestWithoutLogin);
        response
                .then()
                .log().all()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных для входа"));
    }

    @After

    public void deleteCourierAfterTest() {
        // Удаляем курьера
        if (courierId != null) {
            deleteCourier(courierId)
                    .then()
                    .log().all()
                    .statusCode(200);
        }
    }



}
