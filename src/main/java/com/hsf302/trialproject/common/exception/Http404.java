package com.hsf302.trialproject.common.exception;

public class Http404 extends RuntimeException {

    public Http404(String message) {
        super(message);
    }

    public Http404(String message, Throwable cause) {
        super(message, cause);
    }
}
