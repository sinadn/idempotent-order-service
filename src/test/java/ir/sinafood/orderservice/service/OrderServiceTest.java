package ir.sinafood.orderservice.service;


import ir.sinafood.orderservice.dto.CreateOrderRequest;
import ir.sinafood.orderservice.dto.OrderItemRequest;
import ir.sinafood.orderservice.dto.OrderResponse;
import ir.sinafood.orderservice.entity.Order;
import ir.sinafood.orderservice.entity.User;
import ir.sinafood.orderservice.enums.OrderStatus;
import ir.sinafood.orderservice.repository.OrderRepository;
import ir.sinafood.orderservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;


    @Mock
    private UserRepository userRepository;


    @Mock
    private OrderItemService orderItemService;


    @InjectMocks
    private OrderService orderService;




    @Test
    void should_return_existing_order_when_idempotency_key_already_exists() {


        String idempotencyKey = "test-key";
        UUID orderId = UUID.randomUUID();
        Order existingOrder = new Order();
        existingOrder.setId(orderId);

        when(orderRepository.findByIdempotencyKey(idempotencyKey))
                .thenReturn(Optional.of(existingOrder));

        UUID userId = UUID.randomUUID();
        List<OrderItemRequest> items = new ArrayList<>();
        CreateOrderRequest orderRequest = new CreateOrderRequest();
        orderRequest.setUserId(userId);
        orderRequest.setItems(items);


        OrderResponse response =
                orderService.createOrder(
                        orderRequest,
                        idempotencyKey
                );

        assertEquals(orderId, response.getId());

        verify(orderRepository, never())
                .saveAndFlush(any(Order.class));
    }


    @Test
    void should_cancel_open_order_and_create_new_open_order() {


        String idempotencyKey = "test-key";
        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        UUID newOrderId = UUID.randomUUID();

        Order existingOrder = new Order();
        existingOrder.setId(orderId);
        existingOrder.setStatus(OrderStatus.OPEN);
        User existingUser = new User();
        existingUser.setId(userId);
        existingOrder.setUser(existingUser);

        Order newOrder = new Order();
        newOrder.setId(newOrderId);
        newOrder.setStatus(OrderStatus.OPEN);
        newOrder.setUser(existingUser);
        newOrder.setIdempotencyKey(idempotencyKey);

        when(orderRepository.findByIdempotencyKey(idempotencyKey)).thenReturn(Optional.empty() , Optional.of(existingOrder));
        when(orderRepository.findByStatusAndUserId(OrderStatus.OPEN, userId)).thenReturn(Optional.of(existingOrder));
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(orderRepository.saveAndFlush(any(Order.class)))
                .thenReturn(
                        existingOrder,
                        newOrder
                );

        CreateOrderRequest orderRequest = new CreateOrderRequest();
        orderRequest.setUserId(userId);

        OrderResponse response =
                orderService.createOrder(
                        orderRequest,
                        idempotencyKey
                );

        assertEquals(OrderStatus.CANCELLED, existingOrder.getStatus());
        assertEquals(OrderStatus.OPEN, newOrder.getStatus());
        assertEquals(newOrderId, response.getId());


        InOrder inOrder = inOrder(orderRepository);
        inOrder.verify(orderRepository)
                .saveAndFlush(existingOrder);

        inOrder.verify(orderRepository)
                .saveAndFlush(
                        argThat(order ->
                                order.getStatus() == OrderStatus.OPEN
                                        && idempotencyKey.equals(order.getIdempotencyKey())
                                        && order.getUser().equals(existingUser)
                        )
                );

        verify(orderItemService).createOrderItem(orderRequest , newOrder);

    }
}
