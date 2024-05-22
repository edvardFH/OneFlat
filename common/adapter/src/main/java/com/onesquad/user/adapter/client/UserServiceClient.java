package com.onesquad.user.adapter.client;

import com.onesquad.common.application.exception.NotFoundException;
import com.onesquad.user.adapter.dto.UserResponseDTO;
import com.onesquad.user.adapter.mapper.UserDTOMapper;
import com.onesquad.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;


@AllArgsConstructor
public class UserServiceClient implements IUserServiceClient {

    private final String USER_SERVICE_NAME  = "USER-SERVICE";
    private final RestTemplate restTemplate;

    @Override
    public Optional<User> getUserById(UUID userId) {
        String url = "http://" + USER_SERVICE_NAME + "/api/v1/users/{userId}";
        ResponseEntity<UserResponseDTO> response = restTemplate.getForEntity(url, UserResponseDTO.class, userId);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return Optional.of(UserDTOMapper.toDomain(response.getBody()));
        } else {
           return Optional.empty();
        }
    }
}
