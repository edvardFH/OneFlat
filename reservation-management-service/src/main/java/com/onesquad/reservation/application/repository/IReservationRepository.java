package com.onesquad.reservation.application.repository;

import com.onesquad.reservation.domain.Reservation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IReservationRepository {
    Reservation save(Reservation reservation);
    Reservation update(Reservation reservation);
    Optional<Reservation> findById(UUID reservationId);
    List<Reservation> findByAccommodationId(UUID accommodationId);
    List<Reservation> findByUserId(UUID userId);
    void deleteById(UUID reservationId);
}