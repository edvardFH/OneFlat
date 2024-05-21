package com.onesquad.user.adapter.persistence;

import com.onesquad.accommodation.adapter.mapper.UserEntityMapper;
import com.onesquad.user.application.repository.IUserRepository;
import com.onesquad.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class UserRepository implements IUserRepository {

    private IUserJpaRepository userPersistenceRepository;

    @Override
    public Optional<User> findById(UUID id) {
        Optional<UserEntity> userEntity = userPersistenceRepository.findById(id);
        return userEntity.map(UserEntityMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = UserEntityMapper.toEntity(user);
        UserEntity savedEntity = userPersistenceRepository.save(userEntity);
        return UserEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> userEntity = userPersistenceRepository.findByEmail(email);
        return userEntity.map(UserEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        Optional<UserEntity> userEntity = userPersistenceRepository.findByPhoneNumber(phoneNumber);
        return userEntity.map(UserEntityMapper::toDomain);
    }
}
