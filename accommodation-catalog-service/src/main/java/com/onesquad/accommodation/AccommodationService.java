package com.onesquad.accommodation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;

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
