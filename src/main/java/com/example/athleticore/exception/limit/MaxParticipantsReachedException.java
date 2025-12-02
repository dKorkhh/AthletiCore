package com.example.athleticore.exception.limit;

public class MaxParticipantsReachedException extends RuntimeException {
    public MaxParticipantsReachedException(String message) {
        super(message);
    }
}
