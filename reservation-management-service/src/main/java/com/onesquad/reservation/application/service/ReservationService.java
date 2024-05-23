package com.onesquad.reservation.application.service;


import com.onesquad.common.exception.OverlappingReservationException;
import com.onesquad.reservation.application.repository.IReservationRepository;
import com.onesquad.reservation.domain.Reservation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReservationService {

    private final IReservationRepository reservationRepository;


    public Reservation createReservation(Reservation reservation) {
        if (reservation.startDate().before(new Date())) {
            throw new IllegalArgumentException("A reservation cannot start before the current date");
        }
        checkForOverlappingReservations(reservation);

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
        checkForOverlappingReservations(reservation);
        return reservationRepository.update(reservation);
    }

    public void deleteReservation(UUID reservationId) {
        reservationRepository.deleteById(reservationId);
    }


    private void checkForOverlappingReservations(Reservation newReservation) {
        List<Reservation> reservations = reservationRepository.findByAccommodationId(newReservation.accommodation().id());

        reservations.stream()
                .filter(reservation -> !reservation.id().equals(newReservation.id()))
                .forEach(existingReservation -> {
                    if (existingReservation.startDate().before(newReservation.endDate())
                        && existingReservation.endDate().after(newReservation.startDate())
                        || existingReservation.endDate().equals(newReservation.startDate())) {
                        throw new OverlappingReservationException(
                                "Reservation dates overlap with an existing reservation"
                        );
                    }
                });
    }
}

