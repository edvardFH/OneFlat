package com.onesquad.user.application.dto;

public record RegisterRequestAppDTO(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String password
) { }
