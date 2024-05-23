package com.onesquad.reservation.application.service;

import com.onesquad.accommodation.adapter.client.IAccommodationServiceClient;
import com.onesquad.accommodation.domain.Accommodation;
import com.onesquad.common.exception.NotFoundException;
import com.onesquad.reservation.application.repository.IAvailabilitiesRepository;
import com.onesquad.reservation.domain.UnavailabilityPeriod;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AvailabilityService {

    private final IAvailabilitiesRepository availabilitiesRepository;
    private final IAccommodationServiceClient accommodationServiceClient;

    public List<UnavailabilityPeriod> getAccommodationAvailabilities(UUID accommodationId) {
        Optional<Accommodation> accommodation = accommodationServiceClient.getAccommodationById(accommodationId);

        if (accommodation.isEmpty()) {
            throw new NotFoundException("No accommodation found for id " + accommodationId);
        }

        return availabilitiesRepository.findOccupiedPeriodByAccommodationId(accommodationId);
    }
}
