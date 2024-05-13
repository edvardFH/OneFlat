package com.onesquad.reservation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/reservation")
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping(path = "{accommodationId}")
    public AvailabilityCheckResponse isAvailable(@PathVariable("accommodationId") Long accommodationId) {
        boolean isAvailable = reservationService.isAvailable(accommodationId);
        return new AvailabilityCheckResponse(isAvailable);
    }
}
