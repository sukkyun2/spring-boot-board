package com.example.board.api.common.app;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
