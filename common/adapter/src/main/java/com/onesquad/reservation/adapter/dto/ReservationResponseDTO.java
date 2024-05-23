package com.onesquad.reservation.adapter.dto;

import com.onesquad.reservation.domain.ReservationStatus;
import java.util.Date;
import java.util.UUID;

public record ReservationResponseDTO(
        UUID id,
        UUID accommodationId,
        UUID userId,
        Date startDate,
        Date endDate,
        ReservationStatus status,
        String comment
) {}
