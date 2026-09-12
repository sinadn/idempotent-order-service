package ir.sinafood.orderservice.repository;

import ir.sinafood.orderservice.entity.Order;
import ir.sinafood.orderservice.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository
        extends JpaRepository<Order, UUID> {


    Optional<Order> findByStatusAndUserId(OrderStatus status , UUID userId);

    Optional<Order> findByIdempotencyKey(String idempotencyKey);



}