package com.hsf302.trialproject.auth.exception;

public class InvalidRegistrationTokenException extends RuntimeException {
    public InvalidRegistrationTokenException(String message) {
        super(message);
    }
}
