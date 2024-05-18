package com.onesquad.adapter.persistence;

import com.onesquad.application.repository.IUserRepository;
import fr.onesquad.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository implements IUserRepository {

    private IUserPersistenceRepository userPersistenceRepository;

    @Override
    public Optional<User> findById(Long id) {
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
