package ir.sinafood.orderservice.repository;

import ir.sinafood.orderservice.entity.Order;
import ir.sinafood.orderservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, UUID> {

}