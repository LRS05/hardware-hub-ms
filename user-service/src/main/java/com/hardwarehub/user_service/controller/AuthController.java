package com.hardwarehub.user_service.controller;

import com.hardwarehub.user_service.dto.AuthRequestDTO;
import com.hardwarehub.user_service.dto.RegisterRequestDTO;
import com.hardwarehub.user_service.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController
{
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequestDTO requestDTO)
    {
        Map<String, ResponseCookie> jwtCookies = authService.register(requestDTO);

        return setCookiesToHeader(
                HttpStatus.CREATED,
                jwtCookies.get("access-token"),
                jwtCookies.get("refresh-token")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody AuthRequestDTO requestDTO)
    {
        Map<String, ResponseCookie> jwtCookies = authService.login(requestDTO);

        return setCookiesToHeader(
                HttpStatus.NO_CONTENT,
                jwtCookies.get("access-token"),
                jwtCookies.get("refresh-token")
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(HttpServletRequest request)
    {
        ResponseCookie newAccessToken = authService.refresh(request.getCookies());

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, newAccessToken.toString())
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout()
    {
        Map<String, ResponseCookie> emptyCookies = authService.logout();

        return setCookiesToHeader(
                HttpStatus.NO_CONTENT,
                emptyCookies.get("access-token"),
                emptyCookies.get("refresh-token")
        );
    }

    private ResponseEntity<Void> setCookiesToHeader(HttpStatus status, ResponseCookie accessCookie, ResponseCookie refreshCookie)
    {
        return ResponseEntity.status(status)
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .build();
    }
}
