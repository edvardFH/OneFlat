package com.onesquad.user.adapter.mapper;

import com.onesquad.user.adapter.persistence.UserEntity;
import com.onesquad.user.application.repository.UserAppData;
import com.onesquad.user.domain.Email;
import com.onesquad.user.domain.PhoneNumber;


public class UserAppDataMapper {

    public static UserAppData toAppData(UserEntity user) {
        return new UserAppData(
                user.getId(),
                new Email(user.getEmail()),
                new PhoneNumber(user.getPhoneNumber()),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
    }
}
