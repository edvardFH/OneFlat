package com.onesquad.accommodation.adapter.controller;

import com.onesquad.accommodation.adapter.dto.AvailabilityDTO;
import com.onesquad.accommodation.adapter.dto.UpdateAvailabilityRequestDTO;
import com.onesquad.accommodation.adapter.mapper.AvailabilityMapper;
import com.onesquad.accommodation.application.exception.NotFoundException;
import com.onesquad.accommodation.application.service.AccommodationService;
import com.onesquad.accommodation.domain.Availability;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/accommodations")
@AllArgsConstructor
public class AccommodationAvailabilityController {

    private final AccommodationService accommodationService;

    @PostMapping("/{id}/availabilities")
    public ResponseEntity<?> addAvailability(
            @PathVariable("id") UUID id,
            @RequestBody AvailabilityDTO availabilityDTO) {
        try {
            Availability availability = new Availability(availabilityDTO.startDate(), availabilityDTO.endDate());
            Availability addedAvailability = accommodationService.addAvailability(id, availability);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedAvailability);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/availabilities")
    public ResponseEntity<?> updateAvailability(
            @PathVariable("id") UUID id,
            @RequestBody UpdateAvailabilityRequestDTO availabilityRequest) {
        try {
            Availability oldAvailability = new Availability(
                    availabilityRequest.oldStartDate(),
                    availabilityRequest.oldEndDate()
            );
            Availability newAvailability = new Availability(
                    availabilityRequest.newStartDate(),
                    availabilityRequest.newEndDate()
            );
            Availability updatedAvailability = accommodationService.updateAvailability(
                    id,
                    oldAvailability,
                    newAvailability);
            return ResponseEntity.ok(updatedAvailability);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/availabilities")
    public ResponseEntity<?> deleteAvailability(
            @PathVariable("id") UUID id,
            @RequestBody AvailabilityDTO availabilityDTO) {
        try {
            accommodationService.deleteAvailability(id, AvailabilityMapper.toDomain(availabilityDTO));
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
