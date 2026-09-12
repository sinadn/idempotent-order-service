package ir.sinafood.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

    private String name;

    private String email;
}