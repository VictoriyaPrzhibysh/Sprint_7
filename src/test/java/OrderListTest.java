import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static data.OrderListData.ORDERS_LIST_PATH;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OrderListTest extends BaseApiTest {

    @Test
    @DisplayName("Get Orders List")
    @Description("Проверяем, что в тело ответа возвращается список заказов")
    public void testGetOrdersList() {
        Response response = given()
                .contentType("application/json")
                .get(ORDERS_LIST_PATH);

        response.then()
                .log().all()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders", hasSize(greaterThanOrEqualTo(0)));
    }

}
