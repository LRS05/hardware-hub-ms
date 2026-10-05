package com.hardwarehub.order_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record OrderItemResponseDTO(

        @JsonProperty("product_name")
        String productName,

        BigDecimal price,

        Integer quantity
) {
}
