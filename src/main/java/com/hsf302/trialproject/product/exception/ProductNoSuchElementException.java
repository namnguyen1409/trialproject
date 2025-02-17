package com.hsf302.trialproject.product.exception;

import lombok.Getter;

@Getter
public class ProductNoSuchElementException extends RuntimeException {
    private final Long id;

    public ProductNoSuchElementException(String message, Long id) {
        super(message);
        this.id = id;
    }

}
