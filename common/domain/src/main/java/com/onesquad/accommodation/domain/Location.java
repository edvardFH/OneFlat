package com.onesquad.accommodation.domain;

public record Location(
        String street,
        String city,
        String postalCode,
        String country
) {
    public Location {
        if (isNullOrEmpty(street)) {
            throw new IllegalArgumentException("Street must not be empty");
        }
        if (isNullOrEmpty(city)) {
            throw new IllegalArgumentException("City must not be empty");
        }
        if (isNullOrEmpty(postalCode)) {
            throw new IllegalArgumentException("Postal Code must not be empty");
        }
        if (isNullOrEmpty(country)) {
            throw new IllegalArgumentException("Country must not be empty");
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
