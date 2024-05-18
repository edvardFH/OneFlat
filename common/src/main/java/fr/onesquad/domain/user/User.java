package fr.onesquad.domain.user;

import java.time.LocalDate;

public record User(
        Long id,
        Email email,
        PhoneNumber phoneNumber,
        String password,
        String firstName,
        String lastName,
        Role role
) {
}
