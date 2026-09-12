package ir.sinafood.orderservice.repository;

import ir.sinafood.orderservice.entity.Order;
import ir.sinafood.orderservice.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderItemRepository
        extends JpaRepository<OrderItem, UUID> {

}