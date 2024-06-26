package com.onesquad.reservation.adapter.controller;

import com.onesquad.accommodation.adapter.client.IAccommodationServiceClient;
import com.onesquad.accommodation.domain.Accommodation;
import com.onesquad.common.exception.NotFoundException;
import com.onesquad.reservation.adapter.dto.ReservationRequestDTO;
import com.onesquad.reservation.adapter.dto.ReservationResponseDTO;
import com.onesquad.reservation.adapter.mapper.ReservationMapper;
import com.onesquad.reservation.application.service.ReservationService;
import com.onesquad.reservation.domain.Reservation;
import com.onesquad.user.adapter.client.IUserServiceClient;
import com.onesquad.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reservations")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;
    private final IAccommodationServiceClient accommodationServiceClient;
    private final IUserServiceClient userServiceClient;


    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO request) {
        Reservation reservation = getDomainReservation(request, null);
        Reservation createdReservation = reservationService.createReservation(reservation);
        ReservationResponseDTO response = ReservationMapper.toDTO(createdReservation);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationResponseDTO> getReservationById(
            @PathVariable("reservationId") UUID reservationId) {
        Optional<Reservation> reservation = reservationService.getReservationById(reservationId);

        return reservation.map(value -> ResponseEntity.ok(ReservationMapper.toDTO(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @GetMapping("/accommodation/{accommodationId}")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByAccommodationId(
            @PathVariable("accommodationId") UUID accommodationId) {
        List<Reservation> reservations = reservationService.getReservationsByAccommodationId(accommodationId);

        List<ReservationResponseDTO> response = reservations.stream()
                .map(ReservationMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByUserId(
            @PathVariable("userId") UUID userId) {
        List<Reservation> reservations = reservationService.getReservationsByUserId(userId);

        List<ReservationResponseDTO> response = reservations.stream()
                .map(ReservationMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{reservationId}")
    public ResponseEntity<ReservationResponseDTO> updateReservation(
            @PathVariable("reservationId") UUID reservationId,
            @RequestBody ReservationRequestDTO request) {
        checkCoherenceBeforeUpdate(request, reservationId);
        Reservation reservation = getDomainReservation(request, reservationId);

        Reservation updatedReservation = reservationService.updateReservation(reservation);

        ReservationResponseDTO response = ReservationMapper.toDTO(updatedReservation);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable("reservationId") UUID reservationId) {
        reservationService.deleteReservation(reservationId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    private Reservation getDomainReservation(ReservationRequestDTO request, UUID id) {
        Optional<Accommodation> accommodation =
                accommodationServiceClient.getAccommodationById(request.accommodationId());
        if (accommodation.isEmpty()) {
            throw new NotFoundException("Accommodation not found");
        }

        Optional<User> user = userServiceClient.getUserById(request.userId());
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        return ReservationMapper.toDomain(request, id, accommodation.get(), user.get());
    }

    private void checkCoherenceBeforeUpdate(ReservationRequestDTO request, UUID id) {
        Optional<Reservation> existingReservation = reservationService.getReservationById(id);

        if (existingReservation.isEmpty()) {
            throw new NotFoundException("Reservation with id " + id + " not found");
        }

        Reservation oldReservation = existingReservation.get();

        if (!oldReservation.accommodation().id().equals(request.accommodationId())
                || !oldReservation.user().id().equals(request.userId())) {
            throw new IllegalArgumentException("Reservation's accommodation and user can not be changed.");
        }
    }
}
