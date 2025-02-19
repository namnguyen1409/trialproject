package com.hsf302.trialproject.exception;

public class Http500 extends RuntimeException {
    public Http500(String message) {
        super(message);
    }
}
