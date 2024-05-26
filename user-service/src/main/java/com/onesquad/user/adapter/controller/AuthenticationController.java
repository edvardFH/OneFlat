package com.onesquad.user.adapter.controller;

import com.onesquad.user.adapter.dto.AuthenticationRequest;
import com.onesquad.user.adapter.dto.AuthenticationResponse;
import com.onesquad.user.adapter.dto.RegisterRequest;
import com.onesquad.user.adapter.mapper.UserAppDTOMapper;
import com.onesquad.user.application.dto.AuthenticationRequestAppDTO;
import com.onesquad.user.application.dto.AuthenticationResponseAppDTO;
import com.onesquad.user.application.dto.RegisterRequestAppDTO;
import com.onesquad.user.application.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        RegisterRequestAppDTO userRegisterAppDTO = UserAppDTOMapper.toAppDTO(registerRequest);
        AuthenticationResponseAppDTO authenticationAppDTO = authenticationService.register(userRegisterAppDTO);

        return ResponseEntity.ok(UserAppDTOMapper.toAdapterDTO(authenticationAppDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> logIn(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationRequestAppDTO authenticationRequestAppDTO = UserAppDTOMapper.toAppDTO(authenticationRequest);
        AuthenticationResponseAppDTO authenticationResponseAppDTO =
                authenticationService.authenticate(authenticationRequestAppDTO);

        return ResponseEntity.ok(UserAppDTOMapper.toAdapterDTO(authenticationResponseAppDTO));
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logOut() {

        return ResponseEntity.ok().body("Successfully logged out.");
    }
}
