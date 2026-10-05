package com.hardwarehub.order_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record QuantityRequestDTO(

        @NotNull
        @Positive
        Integer quantity
) {
}
