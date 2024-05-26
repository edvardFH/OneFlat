package com.onesquad.user.adapter.dto;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String password
) {
}
