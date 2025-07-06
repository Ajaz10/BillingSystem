package com.supermarket.service;

import com.supermarket.dto.*;
import com.supermarket.entity.User;
import com.supermarket.repository.UserRepository;
import com.supermarket.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserDto register(UserRegistrationDto registrationDto) {
        // Check if username already exists
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new RuntimeException("Username already exists: " + registrationDto.getUsername());
        }

        // Check if email already exists
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Email already exists: " + registrationDto.getEmail());
        }

        // Create user entity
        User user = registrationDto.toEntity();
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        // Save user
        User savedUser = userRepository.save(user);
        
        log.info("User registered successfully: {}", savedUser.getUsername());
        
        return UserDto.fromEntity(savedUser);
    }

    public AuthResponse login(AuthRequest authRequest) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
                )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            // Find user entity
            User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

            // Generate tokens
            String accessToken = jwtUtil.generateToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            log.info("User logged in successfully: {}", user.getUsername());

            return AuthResponse.of(
                accessToken,
                refreshToken,
                86400000L, // 24 hours
                UserDto.fromEntity(user)
            );

        } catch (Exception e) {
            log.error("Login failed for user: {}", authRequest.getUsername(), e);
            throw new RuntimeException("Invalid username or password");
        }
    }

    public AuthResponse refreshToken(String refreshToken) {
        try {
            if (!jwtUtil.isTokenValid(refreshToken)) {
                throw new RuntimeException("Invalid refresh token");
            }

            String username = jwtUtil.extractUsername(refreshToken);
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

            if (!user.getIsActive()) {
                throw new RuntimeException("User account is disabled");
            }

            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())
                .build();

            String newAccessToken = jwtUtil.generateToken(userDetails);
            String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

            return AuthResponse.of(
                newAccessToken,
                newRefreshToken,
                86400000L, // 24 hours
                UserDto.fromEntity(user)
            );

        } catch (Exception e) {
            log.error("Token refresh failed", e);
            throw new RuntimeException("Invalid refresh token");
        }
    }
}