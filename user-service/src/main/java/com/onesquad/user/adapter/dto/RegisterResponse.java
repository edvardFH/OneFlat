package com.onesquad.user.adapter.dto;

public record RegisterResponse(
        String token,
        UserResponseDTO user) {
}
