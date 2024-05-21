package com.onesquad.user.application.repository;

import com.onesquad.user.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository {
    Optional<User> findById(UUID id);
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
}
