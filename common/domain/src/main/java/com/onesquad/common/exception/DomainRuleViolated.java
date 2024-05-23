package com.onesquad.common.exception;

public class DomainRuleViolated extends RuntimeException {
    public DomainRuleViolated(String message) {
        super(message);
    }
}
