package ir.sinafood.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {

    private String name;

    private String description;

}