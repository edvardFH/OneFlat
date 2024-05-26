package com.onesquad.user.application.service;

import com.onesquad.common.exception.FieldValueAlreadyUsedException;
import com.onesquad.common.exception.MalformedDataException;
import com.onesquad.user.application.repository.IUserRepository;
import com.onesquad.user.domain.Email;
import com.onesquad.user.domain.PhoneNumber;
import com.onesquad.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<User> searchUser(UUID id, String email, String phoneNumber) {
        if (id != null) {
            return getUserById(id);
        } else if (email != null) {
            return getUserByEmail(email);
        } else if (phoneNumber != null) {
            return getUserByPhoneNumber(phoneNumber);
        } else {
            throw new IllegalArgumentException("At least one search parameter must be provided");
        }
    }
}
