package com.onesquad.user.adapter.mapper;

import com.onesquad.user.adapter.persistence.UserEntity;
import com.onesquad.user.domain.Email;
import com.onesquad.user.domain.PhoneNumber;
import com.onesquad.user.domain.User;

public class UserEntityMapper {

    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new User(
                entity.getId(),
                new Email(entity.getEmail()),
                new PhoneNumber(entity.getPhoneNumber()),
                entity.getPassword(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getRole()
        );
    }

    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        return new UserEntity(
                user.id(),
                user.email().value(),
                user.phoneNumber().value(),
                user.password(),
                user.firstName(),
                user.lastName(),
                user.role()
        );
    }
}

