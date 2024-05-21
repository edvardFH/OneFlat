package com.onesquad.accommodation.adapter.controller;

import com.onesquad.accommodation.adapter.dto.AccommodationResponseDTO;
import com.onesquad.accommodation.adapter.dto.IsVisibleResponseDTO;
import com.onesquad.accommodation.adapter.mapper.AccommodationDTOMapper;
import com.onesquad.accommodation.application.exception.InvalidSearchCriteriaException;
import com.onesquad.accommodation.application.service.AccommodationService;
import com.onesquad.accommodation.domain.Accommodation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/accommodations")
@AllArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccommodationById(@PathVariable("id") UUID id) {
        Optional<Accommodation> accommodation = accommodationService.getAccommodationById(id);
        return accommodation.map(value -> ResponseEntity.ok(AccommodationDTOMapper.toDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/is-visible")
    public ResponseEntity<?> isAccommodationVisible(@PathVariable("id") UUID id) {
        boolean isVisible = accommodationService.isAccommodationVisible(id);
        return ResponseEntity.ok(new IsVisibleResponseDTO(isVisible));
    }

    @GetMapping("/search")
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
