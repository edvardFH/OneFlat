package com.onesquad.reservation.application.repository;

import com.onesquad.reservation.domain.UnavailabilityPeriod;

import java.util.List;
import java.util.UUID;

public interface IAvailabilitiesRepository {
    List<UnavailabilityPeriod> findOccupiedPeriodByAccommodationId(UUID accommodationId);
}
