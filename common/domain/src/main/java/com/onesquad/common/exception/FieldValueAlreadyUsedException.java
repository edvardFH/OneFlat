package com.onesquad.common.exception;

public class FieldValueAlreadyUsedException extends RuntimeException {
    public FieldValueAlreadyUsedException(String message) {
        super(message);
    }
}
