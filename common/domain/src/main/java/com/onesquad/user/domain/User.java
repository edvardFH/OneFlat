package com.onesquad.user.domain;

import java.util.UUID;

public record User(
        UUID id,
        Email email,
        PhoneNumber phoneNumber,
        String password,
        String firstName,
        String lastName,
        Role role
) {}
