package com.example.athleticore.exception.limit;

public class LimitCallsException extends RuntimeException {
    public LimitCallsException(String message) {
        super(message, null, false, false);
    }
}
