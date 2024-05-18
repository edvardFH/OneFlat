package com.onesquad.adapter.dto;

public record UserResponseDTO(
        Long id,
        String email,
        String phoneNumber,
        String firstName,
        String lastName,
        String role
) {}