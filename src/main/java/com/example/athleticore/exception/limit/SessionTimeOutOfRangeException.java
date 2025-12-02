package com.example.athleticore.exception.limit;

public class SessionTimeOutOfRangeException extends RuntimeException {
    public SessionTimeOutOfRangeException(String message) {
        super(message);
    }
}
