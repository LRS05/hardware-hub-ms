package com.hardwarehub.user_service.mapper;

import com.hardwarehub.user_service.dto.UserResponseDTO;
import com.hardwarehub.user_service.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper
{
    UserResponseDTO entityToDTO(UserEntity user);
}
