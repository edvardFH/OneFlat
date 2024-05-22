package com.onesquad.accommodation.adapter.dto;


import java.util.Set;
import java.util.UUID;

public record AccommodationResponseDTO(
        UUID id,
        UUID ownerId,
        String type,
        LocationDTO location,
        Double price,
        int numberOfRooms,
        int numberOfBathrooms,
        int area,
        String description,
        boolean isVisible,
        Set<AvailabilityDTO> availabilities
) {}
