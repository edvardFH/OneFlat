package com.onesquad.accommodation.adapter.client;


import com.onesquad.accommodation.adapter.dto.AccommodationResponseDTO;
import com.onesquad.accommodation.adapter.mapper.AccommodationDTOMapper;
import com.onesquad.accommodation.domain.Accommodation;
import com.onesquad.user.adapter.client.IUserServiceClient;
import com.onesquad.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;


@AllArgsConstructor
public class AccommodationServiceClient implements IAccommodationServiceClient {

    private final String ACCOMMODATION_SERVICE_NAME = "ACCOMMODATION-CATALOG-SERVICE";
    private final RestTemplate restTemplate;
    private final IUserServiceClient userServiceClient;

    @Override
    public Optional<Accommodation> getAccommodationById(UUID accommodationId) {
        String url = "http://" + ACCOMMODATION_SERVICE_NAME + "/api/v1/accommodations/{accommodationId}";
        ResponseEntity<AccommodationResponseDTO> response = restTemplate.getForEntity(url, AccommodationResponseDTO.class, accommodationId);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            return Optional.empty();
        }

        AccommodationResponseDTO accommodation = response.getBody();
        Optional<User> owner = userServiceClient.getUserById(accommodation.ownerId());


        return owner.map(user -> AccommodationDTOMapper.toDomain(accommodation, user));
    }
}
