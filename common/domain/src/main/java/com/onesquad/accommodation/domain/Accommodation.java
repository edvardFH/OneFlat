package com.onesquad.accommodation.domain;

import com.onesquad.user.domain.User;

import java.util.UUID;

public record Accommodation(
        UUID id,
        User owner,
        AccommodationType type,
        Location location,
        Price price,
        int numberOfRooms,
        int numberOfBathrooms,
        Area area,
        String description
) {
    public Accommodation {
        if (numberOfRooms < 1) {
            throw new IllegalArgumentException("Number of rooms must be at least one");
        }
        if (numberOfBathrooms < 0) {
            throw new IllegalArgumentException("Number of bathrooms cannot be negative");
        }
    }
}
