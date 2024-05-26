package com.onesquad.user.adapter.controller;

import com.onesquad.user.adapter.mapper.UserDTOMapper;
import com.onesquad.common.exception.FieldValueAlreadyUsedException;
import com.onesquad.common.exception.MalformedDataException;
import com.onesquad.user.application.service.UserService;
import com.onesquad.user.adapter.dto.UserCreateDTO;
import com.onesquad.user.adapter.dto.UserResponseDTO;
import com.onesquad.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") UUID userId) {
        try {
            Optional<User> user = userService.getUserById(userId);
            return user.map(value -> ResponseEntity.ok(UserDTOMapper.toDTO(value)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return handleException(e);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUser(
            @RequestParam(name = "i", required = false) UUID id,
            @RequestParam(name = "e", required = false) String email,
            @RequestParam(name = "p", required = false) String phoneNumber) {

        try {
            Optional<User> user = userService.searchUser(id, email, phoneNumber);
            return user.map(value -> ResponseEntity.ok(UserDTOMapper.toDTO(value)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return handleException(e);
        }
    }


    private ResponseEntity<String> handleException(RuntimeException e) {
        return switch (e) {
            case FieldValueAlreadyUsedException ex -> ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
            case MalformedDataException ex -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            case IllegalArgumentException ex -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user data");
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        };
    }
}

