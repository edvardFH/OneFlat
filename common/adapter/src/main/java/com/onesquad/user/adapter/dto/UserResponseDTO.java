package com.onesquad.user.adapter.dto;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String email,
        String phoneNumber,
        String firstName,
        String lastName,
        String role
) {}