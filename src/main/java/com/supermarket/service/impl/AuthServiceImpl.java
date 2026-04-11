package com.supermarket.service.impl;

import com.supermarket.domain.Role;
import com.supermarket.domain.RoleName;
import com.supermarket.domain.User;
import com.supermarket.dto.AuthLoginRequest;
import com.supermarket.dto.AuthRegisterRequest;
import com.supermarket.dto.AuthResponse;
import com.supermarket.exception.BadRequestException;
import com.supermarket.repository.RoleRepository;
import com.supermarket.repository.UserRepository;
import com.supermarket.security.JwtService;
import com.supermarket.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(AuthLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        String token = jwtService.generateToken(user.getUsername(), user.getRole().getName().name());
        log.info("User {} logged in successfully", user.getUsername());
        return new AuthResponse(token, user.getUsername(), user.getRole().getName().name());
    }

    @Override
    @Transactional
    public AuthResponse register(AuthRegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new BadRequestException("Username already exists");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("Email already exists");
        }

        RoleName roleName;
        try {
            roleName = request.roleName() == null || request.roleName().isBlank()
                    ? RoleName.CASHIER
                    : RoleName.valueOf(request.roleName().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Unsupported role. Allowed roles: ADMIN, CASHIER, MANAGER");
        }

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new BadRequestException("Role not found: " + roleName));

        User savedUser = userRepository.save(User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .build());

        String token = jwtService.generateToken(savedUser.getUsername(), savedUser.getRole().getName().name());
        log.info("User {} registered successfully", savedUser.getUsername());
        return new AuthResponse(token, savedUser.getUsername(), savedUser.getRole().getName().name());
    }
}
