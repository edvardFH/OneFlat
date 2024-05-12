package com.onesquad.accommodation;

import org.springframework.stereotype.Service;

@Service
public record AccommodationService(AccommodationRepository accommodationRepository) {
    public void registerAccommodation(AccommodationRegistrationRequest request) {
        Accommodation accommodation = Accommodation.builder()
                .ownerId(request.ownerId())
                .type(request.type())
                .location(request.location())
                .price(request.price())
                .bedrooms(request.bedrooms())
                .bathrooms(request.bathrooms())
                .area(request.area())
                .description(request.description())
                .build();

        // TODO: check fields

        accommodationRepository.save(accommodation);
    }
}
