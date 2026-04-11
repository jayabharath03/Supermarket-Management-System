package com.supermarket.dto;

public record AuthResponse(String token, String username, String role) {
}
