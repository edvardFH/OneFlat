package com.onesquad.reservation.application.service;

import com.onesquad.common.exception.IllegalOperationException;
import com.onesquad.common.exception.NotFoundException;
import com.onesquad.reservation.application.repository.IReservationRepository;
import com.onesquad.reservation.domain.Reservation;
import com.onesquad.reservation.domain.ReservationStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReservationStatusService {

    private final IReservationRepository reservationRepository;


    public Reservation cancelReservation(UUID reservationId) {
        Reservation reservationToCancel = getReservation(reservationId);
        ReservationStatus status  = reservationToCancel.status();

        if (status == ReservationStatus.REJECTED) {
            throw new IllegalOperationException("A rejected reservation can not be cancelled");
        }

        Reservation cancelledReservation = setNewStatus(reservationToCancel, ReservationStatus.CANCELLED);

        return reservationRepository.update(cancelledReservation);
    }


    public Reservation approveReservation(UUID reservationId) {
        return approveOrRejectReservationHelper(reservationId, true);
    }

    public Reservation rejectReservation(UUID reservationId) {
        return approveOrRejectReservationHelper(reservationId, false);
    }

    private Reservation approveOrRejectReservationHelper(UUID reservationId, boolean isAccepted) {
        Reservation reservation = getReservation(reservationId);
        ReservationStatus status = reservation.status();

        if (!status.equals(ReservationStatus.PENDING)) {
            throw new IllegalOperationException("Only a PENDING reservation can be accepted or refused");
        }

        ReservationStatus newStatus = isAccepted
                ? ReservationStatus.APPROVED
                : ReservationStatus.REJECTED;

        Reservation cancelledReservation = setNewStatus(reservation, newStatus);

        return reservationRepository.update(cancelledReservation);
    }


    private Reservation getReservation(UUID reservationId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);

        if (optionalReservation.isEmpty()) {
            throw new NotFoundException("Reservation with id " + reservationId + " not found");
        }

        return optionalReservation.get();
    }


    private Reservation setNewStatus(Reservation reservation, ReservationStatus newStatus) {
        return new Reservation(
                reservation.id(),
                reservation.accommodation(),
                reservation.user(),
                reservation.startDate(),
                reservation.endDate(),
                newStatus,
                reservation.comment());
    }
}
