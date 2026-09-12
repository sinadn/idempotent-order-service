package ir.sinafood.orderservice.entity;

import ir.sinafood.orderservice.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Setter
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(unique = true, nullable = false)
    private String idempotencyKey;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at")
    private Long createdAt = Instant.now().toEpochMilli();


}
