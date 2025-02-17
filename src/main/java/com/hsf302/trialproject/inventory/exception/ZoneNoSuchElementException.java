package com.hsf302.trialproject.inventory.exception;

import lombok.Getter;

@Getter
public class ZoneNoSuchElementException extends RuntimeException {
    private final Long id;

    public ZoneNoSuchElementException(String message, Long id) {
        super(message);
        this.id = id;
    }

}
