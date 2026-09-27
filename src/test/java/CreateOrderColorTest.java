import data.OrderData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static data.OrderData.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderColorTest extends BaseApiTest {

    private final List<String> color;

    public CreateOrderColorTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { Collections.emptyList() },
                { List.of("GREY") },
                { List.of("BLACK") },
                { Arrays.asList("GREY", "BLACK")}
        });
    }

    @Test
    @DisplayName("Create Order With Color")
    @Description("Проверяем, что можно указать один из цветов — BLACK или GREY;\n" +
            "можно указать оба цвета;\n" +
            "можно совсем не указывать цвет;\n" +
            "тело ответа содержит track.")
    public void testCreateOrderWithColor() {

        OrderModel order = new OrderModel(
                OrderData.FIRSTNAME,
                OrderData.LASTNAME,
                OrderData.ADDRESS,
                OrderData.METROSTATION,
                OrderData.PHONE,
                OrderData.RENTTIME,
                OrderData.DELIVERYDATE,
                OrderData.COMMENT,
                color
        );

        Response response = given()
                .contentType("application/json")
                .body(order)
                .post(ORDER_CREATE_PATH);

        response.then()
                .log().all()
                .statusCode(201)
                .body("track", notNullValue())
                .body("track", greaterThan(0));
    }
}
