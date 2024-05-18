package com.onesquad.application.exception;

public class FieldValueAlreadyUsedException extends RuntimeException {
    public FieldValueAlreadyUsedException(String message) {
        super(message);
    }
}
