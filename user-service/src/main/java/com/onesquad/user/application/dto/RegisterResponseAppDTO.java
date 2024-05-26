package com.onesquad.user.application.dto;

import com.onesquad.user.application.repository.UserAppData;

public record RegisterResponseAppDTO(String token, UserAppData user) {
}
