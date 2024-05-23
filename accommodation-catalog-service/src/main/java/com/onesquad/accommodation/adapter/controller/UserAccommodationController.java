package com.onesquad.accommodation.adapter.controller;

import com.onesquad.accommodation.adapter.dto.AccommodationRequestDTO;
import com.onesquad.accommodation.adapter.dto.AccommodationResponseDTO;
import com.onesquad.accommodation.adapter.mapper.AccommodationDTOMapper;
import com.onesquad.common.exception.NotFoundException;
import com.onesquad.accommodation.application.service.AccommodationService;
import com.onesquad.accommodation.domain.Accommodation;
import com.onesquad.user.adapter.dto.UserResponseDTO;
import com.onesquad.user.adapter.mapper.UserDTOMapper;
import com.onesquad.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin
@AllArgsConstructor
public class UserAccommodationController {

    private final AccommodationService accommodationService;
    private final RestTemplate restTemplate;

    private User getUserFromUserService(UUID ownerId) throws NotFoundException {
        String url = "http://USER-SERVICE/api/v1/users/{ownerId}";
        ResponseEntity<UserResponseDTO> response = restTemplate.getForEntity(
                url,
                UserResponseDTO.class,
                ownerId);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return UserDTOMapper.toDomain(response.getBody());
        } else {
            throw new NotFoundException("User with id " + ownerId + " not found");
        }
    }

    @PostMapping("/{ownerId}/accommodations")
    public ResponseEntity<?> createAccommodation(
            @PathVariable("ownerId") UUID ownerId,
            @RequestBody AccommodationRequestDTO accommodationCreateDTO) {
        try {
            User owner = getUserFromUserService(ownerId);
            Accommodation accommodation = AccommodationDTOMapper.toDomain(accommodationCreateDTO, owner);
            Accommodation savedAccommodation = accommodationService.createAccommodation(accommodation);
            AccommodationResponseDTO responseDTO = AccommodationDTOMapper.toDTO(savedAccommodation);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{ownerId}/accommodations/{id}")
    public ResponseEntity<?> updateAccommodation(
            @PathVariable("ownerId") UUID ownerId,
            @PathVariable("id") UUID id,
            @RequestBody AccommodationRequestDTO accommodationUpdateDTO) {
        try {
            User owner = getUserFromUserService(ownerId);
            accommodationService.getAccommodationById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Accommodation not found with id " + id));
            Accommodation updatedAccommodation = AccommodationDTOMapper.toDomain(accommodationUpdateDTO, id, owner);
            Accommodation savedAccommodation = accommodationService.updateAccommodation(updatedAccommodation);
            AccommodationResponseDTO responseDTO = AccommodationDTOMapper.toDTO(savedAccommodation);
            return ResponseEntity.ok(responseDTO);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{ownerId}/accommodations")
    public ResponseEntity<?> getAccommodationsByOwnerId(@PathVariable("ownerId") UUID ownerId) {
        List<Accommodation> accommodations = accommodationService.getAccommodationsByOwnerId(ownerId);
        List<AccommodationResponseDTO> responseDTOs = accommodations.stream()
                .map(AccommodationDTOMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
}
