package ir.sinafood.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;


@Getter
@Setter
public class OrderResponse {
    private UUID id;


    public OrderResponse() {
    }

    public OrderResponse(UUID id) {
        this.id = id;
    }
}
