package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierLoginRequestModel;
import model.CourierModel;

import static data.CourierData.*;
import static io.restassured.RestAssured.given;

public class CourierSteps {
    @Step("Создание курьера")
    public static Response createCourier(CourierModel courier) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER_CREATE_PATH)
                .then()
                .extract().response();
    }
    @Step("Авторизация курьера")
    public static Response loginCourier(CourierLoginRequestModel request) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(COURIER_AUTHORIZATION_PATH)
                .then()
                .extract().response();
    }
    @Step("Удаление курьера по ID")
    public static Response deleteCourier(Integer courierId) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .delete(COURIER_DELETE_PATH + courierId)
                .then()
                .extract().response();
    }
}
