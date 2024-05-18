package com.onesquad.application.repository;

import fr.onesquad.domain.user.User;

import java.util.Optional;

public interface IUserRepository {
    Optional<User> findById(Long id);
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
}
