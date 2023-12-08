package com.linkextractor.backend.exceptions;

/**
 * Custom exception class indicating an invalid login attempt.
 * This exception is thrown when there's an issue with the login credentials provided.
 */
public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String message) {
        super(message);
    }
}
