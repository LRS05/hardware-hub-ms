package com.hardwarehub.user_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterRequestDTO(

        String email,

        String username,

        String password,

        @JsonProperty("first_name")
        String firstName,

        @JsonProperty("last_name")
        String lastName
) {
}
