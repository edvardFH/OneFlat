package com.onesquad.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserPersistenceRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
}
