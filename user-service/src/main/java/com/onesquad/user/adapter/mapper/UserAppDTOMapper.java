package com.onesquad.user.adapter.mapper;

import com.onesquad.user.adapter.dto.AuthenticationRequest;
import com.onesquad.user.adapter.dto.AuthenticationResponse;
import com.onesquad.user.adapter.dto.RegisterRequest;
import com.onesquad.user.application.dto.AuthenticationRequestAppDTO;
import com.onesquad.user.application.dto.AuthenticationResponseAppDTO;
import com.onesquad.user.application.dto.RegisterRequestAppDTO;

public class UserAppDTOMapper {

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

    public static AuthenticationRequestAppDTO toAppDTO(AuthenticationRequest authenticationRequest) {
        return new AuthenticationRequestAppDTO(
                authenticationRequest.email(),
                authenticationRequest.password()
        );
    }
}
