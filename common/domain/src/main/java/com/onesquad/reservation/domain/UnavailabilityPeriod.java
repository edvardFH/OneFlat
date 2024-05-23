package com.onesquad.reservation.domain;

import com.onesquad.common.exception.DomainRuleViolated;

import java.util.Date;

public record UnavailabilityPeriod(Date startDate, Date endDate) {
    public UnavailabilityPeriod {
        if (startDate.after(endDate)) {
            throw new DomainRuleViolated("Start date cannot be after end date");
        }
    }
}
