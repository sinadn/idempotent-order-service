package ir.sinafood.orderservice.controller;

import ir.sinafood.orderservice.dto.CreateOrderRequest;
import ir.sinafood.orderservice.dto.OrderResponse;
import ir.sinafood.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponse create(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestBody CreateOrderRequest request
    ){
        return orderService.createOrder(request , idempotencyKey);
    }


}
