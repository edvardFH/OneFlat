package com.onesquad.reservation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public boolean isAvailable(Long accommodationId) {
        return true;
    }
}
