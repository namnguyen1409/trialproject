package com.hsf302.trialproject.exception;

import lombok.Getter;

@Getter
public class CustomerNoSuchElementException extends RuntimeException {
    private final Long id;

    public CustomerNoSuchElementException(String message, Long id) {
        super(message);
        this.id = id;
    }

}
