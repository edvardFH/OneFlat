package com.onesquad.reservation.service;

import com.onesquad.reservation.repository.ReservationRepository;
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
