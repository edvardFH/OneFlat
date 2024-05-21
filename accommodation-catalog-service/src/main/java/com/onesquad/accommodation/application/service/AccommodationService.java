package com.onesquad.accommodation.application.service;


import com.onesquad.accommodation.adapter.dto.AccommodationRequestDTO;
import com.onesquad.accommodation.adapter.mapper.AccommodationDTOMapper;
import com.onesquad.accommodation.application.exception.InvalidSearchCriteriaException;
import com.onesquad.accommodation.application.exception.NotFoundException;
import com.onesquad.accommodation.application.repository.IAccommodationRepository;
import com.onesquad.accommodation.domain.Accommodation;
import com.onesquad.accommodation.domain.AccommodationType;
import com.onesquad.accommodation.domain.Price;
import com.onesquad.user.adapter.dto.UserResponseDTO;
import com.onesquad.user.adapter.mapper.UserDTOMapper;
import com.onesquad.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccommodationService {

    private IAccommodationRepository accommodationRepository;
    private final RestTemplate restTemplate;

    public Optional<Accommodation> getAccommodationById(UUID id) {
        return accommodationRepository.findById(id);
    }

    public List<Accommodation> getAccommodationsByOwnerId(UUID ownerId) {
        return accommodationRepository.findByOwnerId(ownerId);
    }

    private User getUserFromUserService(UUID ownerId) throws NotFoundException {
        String url = "http://USER-SERVICE/api/v1/users/{ownerId}";
        ResponseEntity<UserResponseDTO> response = restTemplate.getForEntity(
                url,
                UserResponseDTO.class,
                ownerId);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return UserDTOMapper.toDomain(response.getBody());
        } else {
            throw new NotFoundException("User with id " + ownerId + " not found");
        }
    }

    public Accommodation createAccommodation(AccommodationRequestDTO dto, UUID ownerId) throws NotFoundException {
        User owner = getUserFromUserService(ownerId);
        Accommodation accommodation = AccommodationDTOMapper.toDomain(dto, owner);
        return accommodationRepository.save(accommodation);
    }

    public Accommodation updateAccommodation(AccommodationRequestDTO dto, UUID id, UUID ownerId) throws NotFoundException {
        User owner = getUserFromUserService(ownerId);
        Accommodation accommodation = AccommodationDTOMapper.toDomain(dto, id, owner);
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
}
