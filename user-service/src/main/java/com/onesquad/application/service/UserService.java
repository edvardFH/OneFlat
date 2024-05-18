package com.onesquad.application.service;

import com.onesquad.application.exception.FieldValueAlreadyUsedException;
import com.onesquad.application.exception.MalformedDataException;
import com.onesquad.application.repository.IUserRepository;
import fr.onesquad.domain.user.Email;
import fr.onesquad.domain.user.PhoneNumber;
import fr.onesquad.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private IUserRepository userRepository;

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user)
            throws FieldValueAlreadyUsedException, MalformedDataException {
        validateUser(user);
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<User> searchUser(Long id, String email, String phoneNumber) {
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

    private void validateUser(User user) {
        if (getUserByEmail(user.email().value()).isPresent()) {
            throw new FieldValueAlreadyUsedException("Email already taken");
        }

        if (getUserByPhoneNumber(user.phoneNumber().value()).isPresent()) {
            throw new FieldValueAlreadyUsedException("Phone number already taken");
        }

        if (!Email.isEmail(user.email().value())) {
            throw new MalformedDataException("Invalid email format");
        }

        if (!PhoneNumber.isPhoneNumber(user.phoneNumber().value())) {
            throw new MalformedDataException("Invalid phone number format");
        }
    }
}
