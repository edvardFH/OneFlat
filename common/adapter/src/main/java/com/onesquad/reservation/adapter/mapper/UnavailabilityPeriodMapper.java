package com.onesquad.reservation.adapter.mapper;

import com.onesquad.reservation.adapter.dto.UnavailabilityPeriodDTO;
import com.onesquad.reservation.domain.UnavailabilityPeriod;

public class UnavailabilityPeriodMapper {

    public static UnavailabilityPeriod toDomain(UnavailabilityPeriodDTO dto) {
        return new UnavailabilityPeriod(dto.startDate(), dto.endDate());
    }

    public static UnavailabilityPeriodDTO toDTO(UnavailabilityPeriod domain) {
        return new UnavailabilityPeriodDTO(domain.startDate(), domain.endDate());
    }
}