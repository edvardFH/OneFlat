package com.onesquad.accommodation.application.service;

import com.onesquad.accommodation.application.exception.InvalidSearchCriteriaException;
import com.onesquad.accommodation.application.exception.NotFoundException;
import com.onesquad.accommodation.application.repository.IAccommodationRepository;
import com.onesquad.accommodation.domain.Accommodation;
import com.onesquad.accommodation.domain.AccommodationType;
import com.onesquad.accommodation.domain.Availability;
import com.onesquad.accommodation.domain.Price;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        return accommodationRepository.save(accommodation);
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

    public Availability addAvailability(UUID accommodationId, Availability availability) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new NotFoundException("Accommodation not found"));

        accommodation.addAvailability(availability);

        accommodationRepository.save(accommodation);
        return availability;
    }

    public Availability updateAvailability(
            UUID accommodationId, Availability oldAvailability, Availability newAvailability) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new NotFoundException("Accommodation not found"));

        Availability existingAvailability = accommodation.getAvailability(oldAvailability)
                .orElseThrow(() -> new NotFoundException("Availability not found"));

        accommodation.removeAvailability(existingAvailability);
        accommodation.addAvailability(newAvailability);

        accommodationRepository.save(accommodation);
        return newAvailability;
    }

    public void deleteAvailability(UUID accommodationId, Availability availability) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new NotFoundException("Accommodation not found"));

        Availability availabilityToDelete = accommodation.getAvailability(availability)
                .orElseThrow(() -> new NotFoundException("Availability not found"));

        accommodation.removeAvailability(availabilityToDelete);
        accommodationRepository.save(accommodation);
    }
}
