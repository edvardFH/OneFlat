package com.onesquad.adapter.controller;

import com.onesquad.adapter.mapper.UserDTOMapper;
import com.onesquad.application.exception.FieldValueAlreadyUsedException;
import com.onesquad.application.exception.MalformedDataException;
import com.onesquad.application.service.UserService;
import com.onesquad.adapter.dto.UserCreateDTO;
import com.onesquad.adapter.dto.UserResponseDTO;
import fr.onesquad.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreateDTO userRequestDTO) {
        try {
            User user = UserDTOMapper.toDomain(userRequestDTO);
            User createdUser = userService.createUser(user);
            UserResponseDTO responseDTO = UserDTOMapper.toDTO(createdUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (RuntimeException e) {
            return handleException(e);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUser(
            @RequestParam(name = "i", required = false) Long id,
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

