package model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor

public class OrderModel {
    private String firstName;
    private String lastName;
    private String address;
    private Integer metroStation;
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;
}
