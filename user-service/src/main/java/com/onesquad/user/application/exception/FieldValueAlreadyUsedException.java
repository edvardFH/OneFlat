package com.onesquad.user.application.exception;

public class FieldValueAlreadyUsedException extends RuntimeException {
    public FieldValueAlreadyUsedException(String message) {
        super(message);
    }
}
