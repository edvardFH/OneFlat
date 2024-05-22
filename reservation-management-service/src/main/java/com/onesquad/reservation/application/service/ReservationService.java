package com.onesquad.reservation.application.service;


import com.onesquad.accommodation.adapter.client.IAccommodationServiceClient;
import com.onesquad.common.application.exception.NotFoundException;
import com.onesquad.reservation.application.repository.IReservationRepository;
import com.onesquad.reservation.domain.Reservation;
import com.onesquad.user.adapter.client.IUserServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReservationService {

    private final IReservationRepository reservationRepository;
    private final IAccommodationServiceClient accommodationServiceClient;
    private final IUserServiceClient userServiceClient;


    public Reservation createReservation(Reservation reservation) {
        if (accommodationServiceClient.getAccommodationById(reservation.accommodation().id()).isEmpty()) {
            throw new NotFoundException("Accommodation not found");
        }
        if (userServiceClient.getUserById(reservation.user().id()).isEmpty()) {
            throw new NotFoundException("User not found");
        }

        return reservationRepository.save(reservation);
    }


    public Optional<Reservation> getReservationById(UUID reservationId) {
        return reservationRepository.findById(reservationId);
    }


    public List<Reservation> getReservationsByAccommodationId(UUID accommodationId) {
        return reservationRepository.findByAccommodationId(accommodationId);
    }


    public List<Reservation> getReservationsByUserId(UUID userId) {
        return reservationRepository.findByUserId(userId);
    }


    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.update(reservation);
    }


    public void deleteReservation(UUID reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public boolean isAvailable(UUID reservationId) { return true; }
}

