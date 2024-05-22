package com.onesquad.reservation.adapter.dto;

import java.util.Date;
import java.util.UUID;

public record ReservationRequestDTO(
        UUID accommodationId,
        UUID userId,
        Date startDate,
        Date endDate,
        String comment
) {}
