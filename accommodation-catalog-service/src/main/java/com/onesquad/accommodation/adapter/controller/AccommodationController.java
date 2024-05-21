package com.onesquad.accommodation.adapter.controller;

import com.onesquad.accommodation.adapter.dto.AccommodationRequestDTO;
import com.onesquad.accommodation.adapter.dto.AccommodationResponseDTO;
import com.onesquad.accommodation.adapter.mapper.AccommodationDTOMapper;
import com.onesquad.accommodation.application.exception.InvalidSearchCriteriaException;
import com.onesquad.accommodation.application.service.AccommodationService;
import com.onesquad.accommodation.domain.Accommodation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping("/{ownerId}/accommodations")
    public ResponseEntity<?> createAccommodation(
            @PathVariable("ownerId") UUID ownerId,
            @RequestBody AccommodationRequestDTO accommodationCreateDTO) {
        try {
            Accommodation accommodation = accommodationService.createAccommodation(accommodationCreateDTO, ownerId);
            AccommodationResponseDTO responseDTO = AccommodationDTOMapper.toDTO(accommodation);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
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
            Accommodation accommodation = accommodationService.updateAccommodation(accommodationUpdateDTO, id, ownerId);
            AccommodationResponseDTO responseDTO = AccommodationDTOMapper.toDTO(accommodation);
            return ResponseEntity.ok(responseDTO);
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

    @GetMapping("/{ownerId}/accommodations/{id}")
    public ResponseEntity<?> getAccommodationById(@PathVariable("ownerId") UUID ownerId, @PathVariable("id") UUID id) {
        Optional<Accommodation> accommodation = accommodationService.getAccommodationById(id);
        return accommodation.map(value -> ResponseEntity.ok(AccommodationDTOMapper.toDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{ownerId}/accommodations/search")
    public ResponseEntity<?> searchAccommodations(
            @RequestParam(name = "t", required = false) String type,
            @RequestParam(name = "c", required = false) String city,
            @RequestParam(name= "min", required = false) Double minPrice,
            @RequestParam(name = "max", required = false) Double maxPrice) {

        try {
            List<Accommodation> accommodations = accommodationService.searchAccommodations(type, city, minPrice, maxPrice);
            List<AccommodationResponseDTO> responseDTOs = accommodations.stream()
                    .map(AccommodationDTOMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseDTOs);
        } catch (InvalidSearchCriteriaException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
