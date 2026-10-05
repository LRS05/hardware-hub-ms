package com.hardwarehub.order_service.controller;

import com.hardwarehub.order_service.dto.OrderResponseDTO;
import com.hardwarehub.order_service.dto.QuantityRequestDTO;
import com.hardwarehub.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController
{
    private final OrderService orderService;

    @PostMapping("/products/{id}")
    public ResponseEntity<Void> addItemToOrder(@PathVariable int id, @Valid @RequestBody QuantityRequestDTO requestDTO)
    {
        orderService.addItem(id, requestDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable long id, Principal principal)
    {
        orderService.deleteItem(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    // clearCurrent() is responsible for finding the Order using the username provided in the JWTs.
    @GetMapping
    public ResponseEntity<OrderResponseDTO> getCurrentOrder()
    {
        return ResponseEntity.ok(orderService.getCurrent());
    }

    @GetMapping("/all")
    public ResponseEntity<Page<OrderResponseDTO>> getAllOrders(Pageable pageable)
    {
        return ResponseEntity.ok(orderService.getAll(pageable));
    }

    // clearCurrent() is responsible for finding the Order using the username provided in the JWTs.
    @DeleteMapping
    public ResponseEntity<Void> clearCurrentOrder()
    {
        orderService.clearCurrent();
        return ResponseEntity.noContent().build();
    }
}
