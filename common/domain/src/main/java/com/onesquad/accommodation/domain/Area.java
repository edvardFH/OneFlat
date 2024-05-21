package com.onesquad.accommodation.domain;

public record Area(int value) {
    public Area {
        if (value <= 0) {
            throw new IllegalArgumentException("Area must be greater than zero");
        }
    }
}