package com.onesquad.user.adapter.mapper;

import com.onesquad.user.adapter.dto.UserCreateDTO;
import com.onesquad.user.adapter.dto.UserResponseDTO;
import com.onesquad.user.domain.Email;
import com.onesquad.user.domain.PhoneNumber;
import com.onesquad.user.domain.Role;
import com.onesquad.user.domain.User;


public class UserDTOMapper {

    public static User toDomain(UserCreateDTO dto) {
        return new User(
                null,
                new Email(dto.email()),
                new PhoneNumber(dto.phoneNumber()),
                dto.password(),
                dto.firstName(),
                dto.lastName(),
                Role.valueOf(dto.role())
        );
    }

    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.id(),
                user.email().value(),
                user.phoneNumber().value(),
                user.firstName(),
                user.lastName(),
                user.role().name()
        );
    }
}
