package com.onesquad.accommodation.application.service;


import com.onesquad.accommodation.adapter.dto.AccommodationRequestDTO;
import com.onesquad.accommodation.adapter.dto.AccommodationResponseDTO;
import com.onesquad.accommodation.adapter.mapper.AccommodationDTOMapper;
import com.onesquad.accommodation.application.exception.InvalidSearchCriteriaException;
import com.onesquad.accommodation.application.repository.IAccommodationRepository;
import com.onesquad.accommodation.domain.Accommodation;
import com.onesquad.accommodation.domain.AccommodationType;
import com.onesquad.accommodation.domain.Price;
import com.onesquad.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccommodationService {

    private IAccommodationRepository accommodationRepository;

    public Optional<Accommodation> getAccommodationById(UUID id) {
        return accommodationRepository.findById(id);
    }

    public List<Accommodation> getAccommodationsByOwnerId(UUID ownerId) {
        return accommodationRepository.findByOwnerId(ownerId);
    }

    public Accommodation createAccommodation(AccommodationRequestDTO dto, UUID ownerId) {
        User owner = new User(
                ownerId,
                null,
                null,
                null,
                null,
                null,
                null);
        Accommodation accommodation = AccommodationDTOMapper.toDomain(dto, owner);
        return accommodationRepository.save(accommodation);
    }

    public Accommodation updateAccommodation(AccommodationRequestDTO dto, UUID ownerId) {
        User owner = new User(
                ownerId,
                null,
                null,
                null,
                null,
                null,
                null);
        Accommodation accommodation = AccommodationDTOMapper.toDomain(dto, owner);
        return accommodationRepository.save(accommodation);
    }

    public List<Accommodation> searchAccommodations(String type, String city, Double minPrice, Double maxPrice)
            throws InvalidSearchCriteriaException, IllegalArgumentException {
        if (type == null && city == null && minPrice == null && maxPrice == null) {
            throw new InvalidSearchCriteriaException("At least one search criterion must be provided.");
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

        return accommodationRepository.searchAccommodations(accommodationType, city, minimumPrice, maximumPrice);
    }
}
