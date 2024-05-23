package com.onesquad.reservation.adapter.controller;

import com.onesquad.reservation.adapter.dto.UnavailabilityPeriodDTO;
import com.onesquad.reservation.adapter.mapper.UnavailabilityPeriodMapper;
import com.onesquad.reservation.application.service.AvailabilityService;
import com.onesquad.reservation.domain.UnavailabilityPeriod;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @GetMapping("/accommodations/{accommodationId}/occupied-periods")
    public List<UnavailabilityPeriodDTO> getUnavailabilityPeriods(
            @PathVariable("accommodationId")UUID accommodationId) {
        List<UnavailabilityPeriod> unavailabilityPeriods =
                availabilityService.getAccommodationAvailabilities(accommodationId);

        return unavailabilityPeriods.stream()
                        .map(UnavailabilityPeriodMapper::toDTO)
                        .toList();
    }
}
