package com.onesquad.user.domain;

import com.onesquad.common.exception.DomainRuleViolated;

import java.util.regex.Pattern;

public record PhoneNumber(String value) {

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[0-9]{10}$");

    public PhoneNumber {
        if (!isPhoneNumber(value)) {
            throw new DomainRuleViolated("Invalid phone number format");
        }
    }

    public static boolean isPhoneNumber(String value) {
        return PHONE_PATTERN.matcher(value).matches();
    }
}
