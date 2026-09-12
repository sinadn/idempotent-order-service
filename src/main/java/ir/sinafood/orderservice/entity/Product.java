package ir.sinafood.orderservice.entity;

import ir.sinafood.orderservice.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    @Column(name = "created_at")
    private Long createdAt = Instant.now().toEpochMilli();;
}
