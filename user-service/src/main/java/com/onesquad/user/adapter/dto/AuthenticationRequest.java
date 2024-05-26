package com.onesquad.user.adapter.dto;

public record AuthenticationRequest(
        String email,
        String password
) {
}
