package com.hsf302.trialproject.inventory.exception;

import lombok.Getter;

@Getter
public class InventoryNoSuchElementException extends RuntimeException {
    private final Long id;

    public InventoryNoSuchElementException(String message, Long id) {
        super(message);
        this.id = id;
    }

}
