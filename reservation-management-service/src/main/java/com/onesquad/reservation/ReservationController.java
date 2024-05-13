package com.onesquad.reservation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/reservation")
@AllArgsConstructor
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping(path = "{accommodationId}")
    public AvailabilityCheckResponse isAvailable(@PathVariable("accommodationId") Long accommodationId) {
        log.info("Checking availability for accommodation id {}", accommodationId);

        boolean isAvailable = reservationService.isAvailable(accommodationId);
        return new AvailabilityCheckResponse(isAvailable);
    }
}
