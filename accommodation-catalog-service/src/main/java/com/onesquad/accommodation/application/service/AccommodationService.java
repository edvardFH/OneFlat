package com.onesquad.accommodation.application.service;

import com.onesquad.common.exception.InvalidSearchCriteriaException;
import com.onesquad.common.exception.NotFoundException;
import com.onesquad.accommodation.application.repository.IAccommodationRepository;
import com.onesquad.accommodation.domain.Accommodation;
import com.onesquad.accommodation.domain.AccommodationType;
import com.onesquad.accommodation.domain.Price;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccommodationService {

    private final IAccommodationRepository accommodationRepository;

    public Optional<Accommodation> getAccommodationById(UUID id) {
        return accommodationRepository.findById(id);
    }

    public List<Accommodation> getAccommodationsByOwnerId(UUID ownerId) {
        return accommodationRepository.findByOwnerId(ownerId);
    }

    public Accommodation createAccommodation(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    public Accommodation updateAccommodation(Accommodation accommodation) {
        return accommodationRepository.update(accommodation);
    }

    public List<Accommodation> searchAccommodations(String type, String city, Double minPrice, Double maxPrice)
            throws InvalidSearchCriteriaException, IllegalArgumentException {
        if (type == null && city == null && minPrice == null && maxPrice == null) {
            throw new InvalidSearchCriteriaException("At least one search criterion must be provided.");
        }
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new InvalidSearchCriteriaException("Minimum price must be lower than maximum price.");
        }

        AccommodationType accommodationType = type != null
                ? Enum.valueOf(AccommodationType.class, type)
                : null;
        Price minimumPrice = minPrice != null
                ? new Price(minPrice)
                : null;
        Price maximumPrice = maxPrice != null
                ? new Price(maxPrice)
                : null;

        return accommodationRepository.searchAccommodations(
                Optional.ofNullable(accommodationType),
                Optional.ofNullable(city),
                Optional.ofNullable(minimumPrice),
                Optional.ofNullable(maximumPrice));
    }


    public boolean isAccommodationVisible(UUID id) {
        Optional<Accommodation> accommodation = accommodationRepository.findById(id);
        if (accommodation.isPresent()) {
            return accommodation.get().isVisible();
        }

        throw new NotFoundException("Accommodation with id " + id + " is not found");
    }
}
