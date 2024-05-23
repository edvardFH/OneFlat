package com.onesquad.user.domain;

import com.onesquad.common.exception.DomainRuleViolated;

import java.util.regex.Pattern;

public record Email(String value) {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^(.+)@(\\S+)$");

    public Email {
        if (!isEmail(value)) {
            throw new DomainRuleViolated("Invalid email format");
        }
    }

    public static boolean isEmail(String value) {
        return EMAIL_PATTERN.matcher(value).matches();
    }
}

