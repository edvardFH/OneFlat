package com.onesquad.accommodation.application.repository;

import com.onesquad.accommodation.domain.Accommodation;
import com.onesquad.accommodation.domain.AccommodationType;
import com.onesquad.accommodation.domain.Price;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAccommodationRepository {
    Optional<Accommodation> findById(UUID id);
    List<Accommodation> findByOwnerId(UUID ownerId);
    Accommodation save(Accommodation accommodation);
    List<Accommodation> searchAccommodations(AccommodationType type, String city, Price minPrice, Price maxPrice);
}
