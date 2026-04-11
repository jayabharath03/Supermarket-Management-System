package com.supermarket.service;

import com.supermarket.dto.AuthLoginRequest;
import com.supermarket.dto.AuthRegisterRequest;
import com.supermarket.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthLoginRequest request);
    AuthResponse register(AuthRegisterRequest request);
}
