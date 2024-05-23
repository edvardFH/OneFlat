package com.onesquad.accommodation.domain;

import com.onesquad.common.exception.DomainRuleViolated;

public record Price(Double value) {
    public Price {
        if (value == null || value <= 0) {
            throw new DomainRuleViolated("Price must be greater than zero");
        }
    }
}