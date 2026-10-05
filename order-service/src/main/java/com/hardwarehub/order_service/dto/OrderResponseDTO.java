package com.hardwarehub.order_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDTO(

        long id,

        @JsonProperty("total_price")
        BigDecimal totalPrice,

        @JsonProperty("order_items")
        List<OrderItemResponseDTO> orderItems
) {
}
