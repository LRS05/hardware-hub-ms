package com.hardwarehub.order_service.repository;

import com.hardwarehub.order_service.entity.enums.OrderStatus;
import com.hardwarehub.order_service.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer>
{
    Page<OrderEntity> findAllByUserUsernameAndStatusNotOrderByCreatedAtDesc(String username, OrderStatus status, Pageable pageable);

    Optional<OrderEntity> findByUserUsernameAndStatus(String username, OrderStatus status);
}
