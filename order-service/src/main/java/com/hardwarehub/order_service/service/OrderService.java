package com.hardwarehub.order_service.service;

import com.hardwarehub.order_service.repository.OrderItemRepository;
import com.hardwarehub.order_service.mapper.OrderMapper;
import com.hardwarehub.order_service.repository.OrderRepository;
import com.hardwarehub.order_service.entity.enums.OrderStatus;
import com.hardwarehub.order_service.dto.OrderResponseDTO;
import com.hardwarehub.order_service.dto.QuantityRequestDTO;
import com.hardwarehub.order_service.entity.OrderEntity;
import com.hardwarehub.order_service.entity.OrderItemEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService
{
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemRepository orderItemRepository;

//    @Transactional
//    public void addItem(long productId, QuantityRequestDTO requestDTO)
//    {
//        ProductEntity product = findProductByIdOrThrow(productId);
//
//        // Find the active order of the user or throw exception.
//        OrderEntity order = findActiveOrderOrThrow();
//
//        /*
//         *    If the user has already an OrderItem with the product, add the new quantity.
//         *    Else create a new OrderItem.
//         */
//
//        OrderItemEntity item = orderItemRepository.findByOrderIdAndProductId(order.getId(), productId)
//                .orElseGet(() -> buildOrderItem(order, product));
//
//        if (!isProductQuantityAvailable(product, item.getQuantity() + requestDTO.quantity()))
//        {
//            throw new RuntimeException("There is not enough stock for this product.");
//        }
//
//        item.setQuantity(item.getQuantity() + requestDTO.quantity());
//
//        orderItemRepository.save(item);
//    }

    public void deleteItem(long itemId, String username)
    {
        OrderItemEntity item = orderItemRepository.findByIdAndUsername(itemId, username)
                .orElseThrow(() -> new RuntimeException("This product is not on your cart."));

        orderItemRepository.delete(item);
    }

    public OrderResponseDTO getCurrent()
    {
        return orderMapper.entityToDTO(findActiveOrderOrThrow());
    }

    public Page<OrderResponseDTO> getAll(Pageable pageable)
    {
        return findAllExceptActive(pageable).map(orderMapper::entityToDTO);
    }

    public void clearCurrent()
    {
        OrderEntity order = findActiveOrderOrThrow();
        if (order.getOrderItems().isEmpty())
        {
            throw new RuntimeException("Your cart is already empty.");
        }

        order.getOrderItems().clear();
        orderRepository.save(order);
    }

    private OrderEntity findActiveOrderOrThrow()
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return orderRepository.findByUserUsernameAndStatus(username, OrderStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Log in to add products to your cart."));
    }

    private Page<OrderEntity> findAllExceptActive(Pageable pageable)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return orderRepository.findAllByUserUsernameAndStatusNotOrderByCreatedAtDesc(
                username,
                OrderStatus.ACTIVE,
                pageable
        );
    }

//    private ProductEntity findProductByIdOrThrow(long id)
//    {
//        return productRepository.findById((int) id)
//                .orElseThrow(() -> new RuntimeException("Product not found."));
//    }
//
//    private boolean isProductQuantityAvailable(ProductEntity product, int quantity)
//    {
//        return product.getStock() >= quantity;
//    }
//
//    private OrderItemEntity buildOrderItem(OrderEntity order, ProductEntity product)
//    {
//        return OrderItemEntity.builder()
//                .price(product.getPrice())
//                .quantity(0)
//                .order(order)
//                .product(product)
//                .build();
//    }
}