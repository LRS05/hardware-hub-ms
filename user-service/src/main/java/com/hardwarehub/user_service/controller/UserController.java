package com.hardwarehub.user_service.controller;

import com.hardwarehub.user_service.dto.PasswordRequestDTO;
import com.hardwarehub.user_service.dto.UserResponseDTO;
import com.hardwarehub.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController
{
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDTO> getMe()
    {
        return ResponseEntity.ok(userService.getMe());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMe(@Valid @RequestBody PasswordRequestDTO requestDTO)
    {
        userService.deleteMe(requestDTO);
        return ResponseEntity.ok("Your account was successfully deleted.");
    }

}
