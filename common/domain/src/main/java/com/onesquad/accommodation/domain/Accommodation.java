package com.onesquad.accommodation.domain;

import com.onesquad.user.domain.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
        String description,
        boolean isVisible,
        Set<Availability> availabilities
) {
    public Accommodation {
        if (numberOfRooms < 1) {
            throw new IllegalArgumentException("Number of rooms must be at least one");
        }
        if (numberOfBathrooms < 0) {
            throw new IllegalArgumentException("Number of bathrooms cannot be negative");
        }

        availabilities = availabilities == null
                ? new HashSet<>()
                : availabilities;
    }

    public void addAvailability(Availability availability) {
        this.availabilities.add(availability);
    }

    public void removeAvailability(Availability availability) {
        this.availabilities.remove(availability);
    }

    public Optional<Availability> getAvailability(Availability availability) {
        return this.availabilities.stream()
                .filter(av -> av.startDate().equals(availability.startDate())
                        && av.endDate().equals(availability.endDate()))
                .findFirst();
    }
}
