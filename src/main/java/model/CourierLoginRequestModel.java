package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor

public class CourierLoginRequestModel {
    private String login;
    private String password;
}
