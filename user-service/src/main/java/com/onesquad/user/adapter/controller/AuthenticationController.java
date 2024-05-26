package com.onesquad.user.adapter.controller;

import com.onesquad.user.adapter.dto.AuthenticationRequest;
import com.onesquad.user.adapter.dto.AuthenticationResponse;
import com.onesquad.user.adapter.dto.RegisterRequest;
import com.onesquad.user.adapter.dto.RegisterResponse;
import com.onesquad.user.adapter.mapper.UserAppMapper;
import com.onesquad.user.application.dto.AuthenticationRequestAppDTO;
import com.onesquad.user.application.dto.AuthenticationResponseAppDTO;
import com.onesquad.user.application.dto.RegisterRequestAppDTO;
import com.onesquad.user.application.dto.RegisterResponseAppDTO;
import com.onesquad.user.application.service.AuthenticationService;
import com.onesquad.user.application.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private static final int TOKEN_START_INDEX = 7;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        RegisterRequestAppDTO userRegisterAppDTO = UserAppMapper.toAppDTO(registerRequest);
        RegisterResponseAppDTO authenticationAppDTO = authenticationService.register(userRegisterAppDTO);

        return ResponseEntity.ok(UserAppMapper.toAdapterDTO(authenticationAppDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> logIn(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationRequestAppDTO authenticationRequestAppDTO = UserAppMapper.toAppDTO(authenticationRequest);
        AuthenticationResponseAppDTO authenticationResponseAppDTO =
                authenticationService.authenticate(authenticationRequestAppDTO);

        return ResponseEntity.ok(UserAppMapper.toAdapterDTO(authenticationResponseAppDTO));
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logOut() {

        return ResponseEntity.ok().body("Successfully logged out.");
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(
            @RequestHeader(AUTHORIZATION_HEADER) String authorizationHeader) {
        final String token;
        final String userEmail;

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        token = authorizationHeader.substring(TOKEN_START_INDEX);
        userEmail = jwtService.extractUsername(token);

        if (userEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails user = userDetailsService.loadUserByUsername(userEmail);

        if (!jwtService.isTokenValid(token, user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().build();
    }
}
