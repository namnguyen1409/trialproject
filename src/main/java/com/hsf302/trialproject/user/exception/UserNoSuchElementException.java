package com.hsf302.trialproject.user.exception;

import lombok.Getter;

@Getter
public class UserNoSuchElementException extends RuntimeException {
    private final Long id;

    public UserNoSuchElementException(String message, Long id) {
        super(message);
        this.id = id;
    }

}
