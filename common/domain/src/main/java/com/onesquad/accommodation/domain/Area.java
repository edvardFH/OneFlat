package com.onesquad.accommodation.domain;

import com.onesquad.common.exception.DomainRuleViolated;

public record Area(int value) {
    public Area {
        if (value <= 0) {
            throw new DomainRuleViolated("Area must be greater than zero");
        }
    }
}