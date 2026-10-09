package com.hardwarehub.order_service.entity;

import com.hardwarehub.order_service.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrderItemEntity> orderItems;
}
