package com.onesquad.accommodation.domain;

import com.onesquad.common.exception.DomainRuleViolated;

public record Location(
        String street,
        String city,
        String postalCode,
        String country
) {
    public Location {
        if (isNullOrEmpty(street)) {
            throw new DomainRuleViolated("Street must not be empty");
        }
        if (isNullOrEmpty(city)) {
            throw new DomainRuleViolated("City must not be empty");
        }
        if (isNullOrEmpty(postalCode)) {
            throw new DomainRuleViolated("Postal Code must not be empty");
        }
        if (isNullOrEmpty(country)) {
            throw new DomainRuleViolated("Country must not be empty");
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
