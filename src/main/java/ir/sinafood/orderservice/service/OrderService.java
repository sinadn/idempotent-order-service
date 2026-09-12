package ir.sinafood.orderservice.service;

import ir.sinafood.orderservice.dto.CreateOrderRequest;
import ir.sinafood.orderservice.dto.OrderResponse;
import ir.sinafood.orderservice.entity.Order;
import ir.sinafood.orderservice.entity.User;
import ir.sinafood.orderservice.enums.OrderStatus;
import ir.sinafood.orderservice.exception.OrderCreationException;
import ir.sinafood.orderservice.exception.UserNotFoundException;
import ir.sinafood.orderservice.repository.OrderRepository;
import ir.sinafood.orderservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final UserRepository userRepository;


    public OrderService(OrderRepository orderRepository, OrderItemService orderItemService , UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request, String idempotencyKey) {

        Optional<Order> existing =
                orderRepository.findByIdempotencyKey(idempotencyKey);


        if (existing.isPresent()) {
            return new OrderResponse(existing.get().getId());
        }


        UUID userId = request.getUserId();
        cancelOrder(userId);
        Order savedOrder;

        try {
            savedOrder = createNewOrder(idempotencyKey , userId);

        } catch (DataIntegrityViolationException e) {
            return orderRepository.findByIdempotencyKey(idempotencyKey)
                    .map(o -> new OrderResponse(o.getId())).orElseThrow(() ->
                            new OrderCreationException(
                                    "Order creation failed"
                            ));
        }

        orderItemService.createOrderItem(
                request,
                savedOrder
        );

        return new OrderResponse(savedOrder.getId());

    }


    private void cancelOrder(UUID userId) {
        orderRepository.findByStatusAndUserId(OrderStatus.OPEN , userId)
                .ifPresent(order -> {
                    order.setStatus(OrderStatus.CANCELLED);
                    orderRepository.saveAndFlush(order);
                });
    }


    private Order createNewOrder(String idempotencyKey , UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));


        Order order = new Order();
        order.setStatus(OrderStatus.OPEN);
        order.setIdempotencyKey(idempotencyKey);
        order.setUser(user);
        return orderRepository.saveAndFlush(order);
    }


}