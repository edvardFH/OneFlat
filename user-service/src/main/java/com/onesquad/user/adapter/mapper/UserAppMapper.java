package com.onesquad.user.adapter.mapper;

import com.onesquad.user.adapter.dto.AuthenticationRequest;
import com.onesquad.user.adapter.dto.AuthenticationResponse;
import com.onesquad.user.adapter.dto.RegisterRequest;
import com.onesquad.user.adapter.dto.RegisterResponse;
import com.onesquad.user.adapter.dto.UserResponseDTO;
import com.onesquad.user.adapter.persistence.UserEntity;
import com.onesquad.user.application.dto.AuthenticationRequestAppDTO;
import com.onesquad.user.application.dto.AuthenticationResponseAppDTO;
import com.onesquad.user.application.dto.RegisterRequestAppDTO;
import com.onesquad.user.application.dto.RegisterResponseAppDTO;
import com.onesquad.user.application.repository.UserAppData;
import com.onesquad.user.domain.Email;
import com.onesquad.user.domain.PhoneNumber;
import com.onesquad.user.domain.User;

public class UserAppMapper {

    public static RegisterRequestAppDTO toAppDTO(RegisterRequest registerRequest) {
        return new RegisterRequestAppDTO(
                registerRequest.firstName(),
                registerRequest.lastName(),
                registerRequest.email(),
                registerRequest.phoneNumber(),
                registerRequest.password()
        );
    }

    public static AuthenticationResponse toAdapterDTO(AuthenticationResponseAppDTO authenticationAppDTO) {
        return new AuthenticationResponse(authenticationAppDTO.token());
    }

    public static RegisterResponse toAdapterDTO(RegisterResponseAppDTO registerResponseAppDTO) {
        return new RegisterResponse(
                registerResponseAppDTO.token(),
                toDTO(registerResponseAppDTO.user()));
    }

    public static AuthenticationRequestAppDTO toAppDTO(AuthenticationRequest authenticationRequest) {
        return new AuthenticationRequestAppDTO(
                authenticationRequest.email(),
                authenticationRequest.password()
        );
    }

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

    public static UserResponseDTO toDTO(UserAppData user) {
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
