package com.onesquad.reservation.adapter.controller;

import com.onesquad.reservation.adapter.dto.ReservationResponseDTO;
import com.onesquad.reservation.adapter.mapper.ReservationMapper;
import com.onesquad.reservation.application.service.ReservationStatusService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservations")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class ReservationStatusController {

    private final ReservationStatusService reservationStatusService;

    @PutMapping("/{reservationId}/cancel")
    public ResponseEntity<ReservationResponseDTO> cancelReservation(
            @PathVariable("reservationId") UUID reservationId) {
        ReservationResponseDTO response = ReservationMapper.toDTO(
                reservationStatusService.cancelReservation(reservationId));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{reservationId}/approve")
    public ResponseEntity<ReservationResponseDTO> approveReservation(
            @PathVariable("reservationId") UUID reservationId) {
        ReservationResponseDTO response = ReservationMapper.toDTO(
                reservationStatusService.approveReservation(reservationId));

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{reservationId}/reject")
    public ResponseEntity<ReservationResponseDTO> rejectReservation(
            @PathVariable("reservationId") UUID reservationId) {
        ReservationResponseDTO response = ReservationMapper.toDTO(
                reservationStatusService.rejectReservation(reservationId));

        return ResponseEntity.ok(response);
    }
}
