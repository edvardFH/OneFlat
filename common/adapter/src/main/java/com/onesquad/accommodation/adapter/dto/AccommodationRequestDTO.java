package com.onesquad.accommodation.adapter.dto;

import java.util.Set;

public record AccommodationRequestDTO(
        String type,
        LocationDTO location,
        Double price,
        int numberOfRooms,
        int numberOfBathrooms,
        int area,
        String description,
        boolean isVisible
) {}