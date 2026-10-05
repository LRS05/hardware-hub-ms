package com.hardwarehub.order_service.mapper;

import com.hardwarehub.order_service.dto.OrderItemResponseDTO;
import com.hardwarehub.order_service.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper
{
    @Mapping(source = "product.name", target = "productName")
    OrderItemResponseDTO entityToDTO(OrderItemEntity orderItem);
}
