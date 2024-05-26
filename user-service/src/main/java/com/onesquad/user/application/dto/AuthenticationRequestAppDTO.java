package com.onesquad.user.application.dto;

public record AuthenticationRequestAppDTO(
        String email,
        String password
) {
}
