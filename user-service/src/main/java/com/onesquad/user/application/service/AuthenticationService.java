package com.onesquad.user.application.service;

import com.onesquad.common.exception.NotFoundException;
import com.onesquad.user.application.dto.AuthenticationRequestAppDTO;
import com.onesquad.user.application.dto.AuthenticationResponseAppDTO;
import com.onesquad.user.application.dto.RegisterRequestAppDTO;
import com.onesquad.user.application.mapper.UserDomainMapper;
import com.onesquad.user.application.repository.IUserRepository;
import com.onesquad.user.application.repository.UserAppData;
import com.onesquad.user.domain.Email;
import com.onesquad.user.domain.PhoneNumber;
import com.onesquad.user.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseAppDTO register(RegisterRequestAppDTO request) {
        var user = new UserAppData(
                null,
                new Email(request.email()),
                new PhoneNumber(request.phoneNumber()),
                passwordEncoder.encode(request.password()),
                request.firstName(),
                request.lastName(),
                Role.USER);

        userRepository.save(UserDomainMapper.toDomain(user));
        var jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponseAppDTO(jwtToken);
    }

    public AuthenticationResponseAppDTO authenticate(AuthenticationRequestAppDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password())
        );

        var user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() -> new NotFoundException("No user with email " + request.email() + " found."));
        var jwtToken = jwtService.generateToken(UserDomainMapper.toAppData(user));

        return new AuthenticationResponseAppDTO(jwtToken);
    }

    public AuthenticationResponseAppDTO logOut() {
        // TODO: implement logout
        return null;
    }
}
