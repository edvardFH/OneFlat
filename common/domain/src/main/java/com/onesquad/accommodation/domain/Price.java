package com.onesquad.accommodation.domain;

public record Price(Double value) {
    public Price {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
    }
}