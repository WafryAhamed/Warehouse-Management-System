package com.wafry.warehouse.controller;

import com.wafry.warehouse.dto.AuthRequest;
import com.wafry.warehouse.dto.AuthResponse;
import com.wafry.warehouse.dto.UserDTO;
import com.wafry.warehouse.security.CustomUserDetails;
import com.wafry.warehouse.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        log.info("Login request received for user: {}", request.getUsername());
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'INVENTORY_MANAGER', 'CASHIER', 'SALES_ANALYST', 'CUSTOMER_SUPPORT')")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserDTO user = authService.getCurrentUser(userDetails);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO user = authService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Inventory Backend is running");
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'INVENTORY_MANAGER', 'CASHIER', 'SALES_ANALYST', 'CUSTOMER_SUPPORT')")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        log.info("Logout request received");
        authService.logout(token);
        return ResponseEntity.ok("{\"message\": \"Logged out successfully\"}");
    }
}
