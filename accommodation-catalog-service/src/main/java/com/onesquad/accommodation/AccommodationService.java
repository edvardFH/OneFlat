package com.onesquad.accommodation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final RestTemplate restTemplate;

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
        accommodationRepository.saveAndFlush(accommodation);
        // TODO: check if available (just to test service communication)

        AvailabilityCheckResponse availabilityCheckResponse = restTemplate.getForObject(
                "http://localhost:8081/api/v1/reservation/{accommodationId}",
                AvailabilityCheckResponse.class,
                accommodation.getId()
        );

        if (availabilityCheckResponse != null && availabilityCheckResponse.isAvailable()) {
            log.info("What a surprise! The newly added accommodation {} is available.", accommodation.getId());
        }
    }

    public Optional<Accommodation> getAccommodationById(long id) {
        return accommodationRepository.findById(id);
    }


}
