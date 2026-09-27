package data;

import model.OrderModel;

import java.util.List;

public class OrderData {

    public static final String ORDER_CREATE_PATH = "/api/v1/orders";

    public static final String FIRSTNAME = "Naruto";
    public static final String LASTNAME = "Uchiha";
    public static final String ADDRESS = "Konoha, 142 apt.";
    public static final Integer METROSTATION = 4;
    public static final String PHONE = "+7 800 355 35 35";
    public static final Integer RENTTIME = 5;
    public static final String DELIVERYDATE = "2020-06-06";
    public static final String COMMENT = "Saske, come back to Konoha";

    public static OrderModel getBaseOrderWithEmptyColor() {
        return new OrderModel(FIRSTNAME, LASTNAME, ADDRESS, METROSTATION, PHONE, RENTTIME, DELIVERYDATE, COMMENT, List.of());
    }
}
