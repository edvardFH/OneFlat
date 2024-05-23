package com.onesquad.reservation.adapter.persistence;

import com.onesquad.reservation.application.repository.IAvailabilitiesRepository;
import com.onesquad.reservation.domain.UnavailabilityPeriod;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class AvailabilitiesRepository implements IAvailabilitiesRepository {

    private final IReservationJpaRepository reservationRepository;

    @Override
    public List<UnavailabilityPeriod> findOccupiedPeriodByAccommodationId(UUID accommodationId) {
        return reservationRepository.findApprovedReservationsByAccommodationId(accommodationId)
                .stream()
                .map(reservationEntity ->
                        new UnavailabilityPeriod(reservationEntity.getStartDate(), reservationEntity.getEndDate()))
                .toList();
    }
}

