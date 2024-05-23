package com.onesquad.accommodation.domain;

import com.onesquad.common.exception.DomainRuleViolated;
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
        boolean isVisible
) {
    public Accommodation {
        if (numberOfRooms < 1) {
            throw new DomainRuleViolated("Number of rooms must be at least one");
        }
        if (numberOfBathrooms < 0) {
            throw new DomainRuleViolated("Number of bathrooms cannot be negative");
        }
    }
}
