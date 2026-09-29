import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CourierLoginRequestModel;
import model.CourierModel;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;

import static data.CourierData.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.CourierSteps.*;

public class CreateCourierTest extends BaseApiTest {

    private CourierModel courier;

    @Test
    @DisplayName("Create Courier Success")
    @Description("Проверяем, что курьера можно создать")
    public void testCreateCourierSuccess() {
        // 1. Создаём курьера
        CourierModel courier = new CourierModel(getRandomLogin(), getRandomPassword(), FIRSTNAME);
        Response response = createCourier(courier);
        response
                .then()
                .log().all()
                .statusCode(201)
                .body("ok", equalTo(true));

    }

    @Test
    @DisplayName("Create Duplicate Courier Fails")
    @Description("Проверяем, что нельзя создать двух одинаковых курьеров")
    public void testCreateDuplicateCourierFails() {
        // 1. Создаём первого курьера
        CourierModel courier = new CourierModel(getRandomLogin(), getRandomPassword(), FIRSTNAME);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(201)
                .body("ok", equalTo(true));

        // 2. Создаем второго курьера с теми же данными
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(409)
                .body("message", Matchers.containsString("Этот логин уже используется"));


    }

    @Test
    @DisplayName("Create Courier Without Login Fails")
    @Description("Проверяем, что чтобы создать курьера, нужно передать в ручку все обязательные поля(без логина)")
    public void testCreateCourierWithoutLoginFails() {
        // Создаём курьера без логина
        CourierModel courier = new CourierModel(null, getRandomPassword(), FIRSTNAME);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create Courier Without Password Fails")
    @Description("Проверяем, что чтобы создать курьера, нужно передать в ручку все обязательные поля(без пароля)")
    public void testCreateCourierWithoutPasswordFails() {
        // Создаём курьера без пароля
        CourierModel courier = new CourierModel(getRandomLogin(), null, FIRSTNAME);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create Courier Without FirstName Fails")
    @Description("Проверяем, что чтобы создать курьера, нужно передать в ручку все обязательные поля(без имени)")
    public void testCreateCourierWithoutFirstNameFails() {
        // Создаём курьера без имени, т.к. в документации поле не отмечено как "необязательное", но по факту поле не является обязательным и выходит код 201
        CourierModel courier = new CourierModel(getRandomLogin(), getRandomPassword(), null);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After

    public void deleteCourierAfterTest() {
        // проверяем, что курьер был создан и логинимся чтобы получить id для удаления курьера,
        if (courier != null && courier.getLogin() != null && courier.getPassword() != null) {
            CourierLoginRequestModel loginRequest =
                    new CourierLoginRequestModel(courier.getLogin(), courier.getPassword());
            Response loginResponse = loginCourier(loginRequest);
            Integer courierId = loginResponse.jsonPath().getInt("id");
            if (courierId != null) {
        // Удаляем курьера
        deleteCourier(courierId)
                .then()
                .log().all()
                .statusCode(200);
            }
        }
    }
}
