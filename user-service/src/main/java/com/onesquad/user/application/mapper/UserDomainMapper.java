package com.onesquad.user.application.mapper;

import com.onesquad.user.application.repository.UserAppData;
import com.onesquad.user.domain.User;

public class UserDomainMapper {

    public static UserAppData toAppData(User user) {
        return new UserAppData(
                user.id(),
                user.email(),
                user.phoneNumber(),
                user.password(),
                user.firstName(),
                user.lastName(),
                user.role()
        );
    }

    public static User toDomain(UserAppData userDetails) {
        return new User(
                userDetails.id(),
                userDetails.email(),
                userDetails.phoneNumber(),
                userDetails.password(),
                userDetails.firstName(),
                userDetails.lastName(),
                userDetails.role()
        );
    }
}
