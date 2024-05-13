package com.onesquad.accommodation;

import java.util.UUID;


public record AccommodationRegistrationRequest(
        Long ownerId,
        String type,
        String location,
        double price,
        int bedrooms,
        int bathrooms,
        double area,
        String description
) {}
