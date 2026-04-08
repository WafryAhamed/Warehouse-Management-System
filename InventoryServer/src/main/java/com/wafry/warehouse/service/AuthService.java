package com.wafry.warehouse.service;

import com.wafry.warehouse.dto.AuthRequest;
import com.wafry.warehouse.dto.AuthResponse;
import com.wafry.warehouse.dto.UserDTO;
import com.wafry.warehouse.entity.User;
import com.wafry.warehouse.exception.InvalidInputException;
import com.wafry.warehouse.exception.ResourceNotFoundException;
import com.wafry.warehouse.repository.UserRepository;
import com.wafry.warehouse.security.CustomUserDetails;
import com.wafry.warehouse.security.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    // Token blacklist - in-memory storage (production should use Redis or DB)
    private static final Set<String> tokenBlacklist = new HashSet<>();

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider,
                      UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public AuthResponse login(AuthRequest request) {
        if (request.getUsername() == null || request.getPassword() == null) {
            throw new InvalidInputException("Username and password are required");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();

            // Update last login time
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);

            String token = tokenProvider.generateToken(userDetails);
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTO.setRoleName(user.getRole().getRoleName());

            log.info("User logged in successfully: {}", user.getUsername());

            return AuthResponse.builder()
                    .token(token)
                    .user(userDTO)
                    .message("Login successful")
                    .build();

        } catch (Exception e) {
            log.error("Authentication failed for user: {}", request.getUsername());
            throw new InvalidInputException("Invalid username or password");
        }
    }

    public UserDTO getCurrentUser(CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRoleName(user.getRole().getRoleName());
        return userDTO;
    }

    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRoleName(user.getRole().getRoleName());
        return userDTO;
    }

    /**
     * Logout user by adding token to blacklist
     * @param token JWT token to revoke
     */
    public void logout(String token) {
        if (token != null) {
            // Remove "Bearer " prefix if present
            String cleanToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            tokenBlacklist.add(cleanToken);
            log.info("Token revoked (added to blacklist)");
        }
    }

    /**
     * Check if token is blacklisted/revoked
     * @param token JWT token to check
     * @return true if token is blacklisted, false otherwise
     */
    public boolean isTokenRevoked(String token) {
        if (token == null) {
            return true;
        }
        String cleanToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        return tokenBlacklist.contains(cleanToken);
    }
}
