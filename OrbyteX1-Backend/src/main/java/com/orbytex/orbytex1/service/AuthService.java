package com.orbytex.orbytex1.service;

import com.orbytex.orbytex1.dto.AuthRequest;
import com.orbytex.orbytex1.dto.AuthResponse;
import com.orbytex.orbytex1.dto.UserDTO;
import com.orbytex.orbytex1.entity.User;
import com.orbytex.orbytex1.exception.InvalidInputException;
import com.orbytex.orbytex1.exception.ResourceNotFoundException;
import com.orbytex.orbytex1.repository.UserRepository;
import com.orbytex.orbytex1.security.CustomUserDetails;
import com.orbytex.orbytex1.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

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
}

