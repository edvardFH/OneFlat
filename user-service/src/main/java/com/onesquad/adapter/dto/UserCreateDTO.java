package com.onesquad.adapter.dto;

public record UserCreateDTO(
        String email,
        String phoneNumber,
        String password,
        String firstName,
        String lastName,
        String role
) {}