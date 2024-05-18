package com.onesquad.accommodation.dto;


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
