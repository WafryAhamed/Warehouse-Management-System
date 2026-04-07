package com.orbytex.orbytex1.controller;

import com.orbytex.orbytex1.dto.AuthRequest;
import com.orbytex.orbytex1.dto.AuthResponse;
import com.orbytex.orbytex1.dto.UserDTO;
import com.orbytex.orbytex1.security.CustomUserDetails;
import com.orbytex.orbytex1.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        log.info("Login request received for user: {}", request.getUsername());
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserDTO user = authService.getCurrentUser(userDetails);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO user = authService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OrbyteX1 Backend is running");
    }
}

