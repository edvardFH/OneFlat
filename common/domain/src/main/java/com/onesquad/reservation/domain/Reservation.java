package com.onesquad.reservation.domain;

import com.onesquad.accommodation.domain.Accommodation;
import com.onesquad.common.exception.DomainRuleViolated;
import com.onesquad.user.domain.User;

import java.util.Date;
import java.util.UUID;

public record Reservation(
        UUID id,
        Accommodation accommodation,
        User user,
        Date startDate,
        Date endDate,
        ReservationStatus status,
        String comment
) {
    public Reservation {
        if (startDate.after(endDate)) {
            throw new DomainRuleViolated("Start date cannot be after end date");
        }
    }
}